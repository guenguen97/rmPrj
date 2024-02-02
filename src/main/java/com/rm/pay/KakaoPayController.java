package com.rm.pay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.subscribe.SubscribeRequest;
import com.rm.subscribe.SubscribeResponse;
import com.rm.subscribe.SubscribeService;
import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;
import com.rm.util.Alert;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final UserService userService;
    private final SubscribeService subscribeService;

    @Autowired
    private HttpSession httpSession;

    //결제 요청
    @PostMapping("/ready")
    public void readyToKakaoPay(HttpServletResponse response, @ModelAttribute("subscribeRequest")  SubscribeRequest subscribeRequest,
                                Principal principal) throws IOException {
        //결제 신청한 구독 정보들 미리 저장해놓기
        httpSession.setAttribute("subscribeRequest", subscribeRequest);

        kakaoPayService.kakaoPayReady(response,subscribeRequest,principal);
    }



    //결제 요청 성공
    @GetMapping("/success")
    public String afterPayRequest(@RequestParam("pg_token") String pgToken,
                                  Principal principal, Model model, RedirectAttributes redirectAttributes) {
        SiteUserResponse user= userService.findUserByLoginID(principal.getName());
        Alert alert=new Alert();

        //전에 저장했던 구독 정보들 다시 갖고오기
        SubscribeRequest subscribeRequest = (SubscribeRequest) httpSession.getAttribute("subscribeRequest");

        subscribeRequest.setSiteUserId(user.getId());


        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken,principal);
//        kakaoApprove.setId(1L);
        System.out.println(kakaoApprove.getPartner_order_id()+"!!!!!!!!!!!!!!!!!");
        //결제 기록 저장하기
        kakaoPayService.savePayRecord(kakaoApprove,user.getId());



        //구독 기록 저장하기, 구독 연장 신청시에는 따로 저장안하고 업데이트만 해주기
        if(subscribeRequest.getRank() !=null) {
            subscribeService.makeSubscribe(subscribeRequest);
        } else {
            System.out.println("구독 연장 신청 함수 !!!!!!!!!!!!!!!!!!!");
            SubscribeResponse originSub=subscribeService.getSubscribeByUserId(user.getId());
            //기존에 있던 구독 기간이랑 요청 들어온 구독 연장 기간 더하기
            subscribeRequest.setId(user.getId());
            subscribeRequest.setPeriod(subscribeRequest.getPeriod()+originSub.getPeriod());

            System.out.println("현재 구독 id "+subscribeRequest.getId() +"현재 연장된 기간" +subscribeRequest.getPeriod());
             subscribeService.updatePeriod(subscribeRequest);

             //알림창 나오게 하기 위해 정보 전달
            redirectAttributes.addAttribute("alertKind", "updatePeriod");

            return "redirect:/";
          }

        //알림창 나오게 하기 위해 정보 전달
        redirectAttributes.addAttribute("alertKind", "createSubscribe");

//        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
        return "redirect:/";
    }

    //결제 진행 중 취소
    @GetMapping("/cancel")
    public String cancel() {
        return "subscribe";
    }

   // 결제 실패
    @GetMapping("/fail")
    public String fail() {

        return "subscribe";
    }
}