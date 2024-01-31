package com.rm.pay;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.subscribe.SubscribeRequest;
import com.rm.subscribe.SubscribeService;
import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final UserService userService;
    private final SubscribeService subscribeService;

    @Autowired
    private HttpSession httpSession;




    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public void readyToKakaoPay(HttpServletResponse response,  @ModelAttribute("subscribeRequest") SubscribeRequest subscribeRequest,
                                Principal principal) throws IOException {
        httpSession.setAttribute("subscribeRequest", subscribeRequest);

        kakaoPayService.kakaoPayReady(response,subscribeRequest,principal);
    }

    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken,
             Principal principal) {
        SiteUserResponse user= userService.findUserByUserID(principal.getName());

        SubscribeRequest subscribeRequest = (SubscribeRequest) httpSession.getAttribute("subscribeRequest");

        subscribeRequest.setSiteUserId(user.getId());


        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken,principal);
//        kakaoApprove.setId(1L);
        System.out.println(kakaoApprove.getPartner_order_id()+"!!!!!!!!!!!!!!!!!");
        kakaoPayService.savePayRecord(kakaoApprove,user.getId());
        subscribeService.makeSubscribe(subscribeRequest);


        System.out.println("완료 되기 직전!!!!!!!!!!!");
        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/cancel")
    public String cancel() {
        return "main";
    }

    /**
     * 결제 실패
     */
    @GetMapping("/fail")
    public String fail() {

        return "main";
    }
}