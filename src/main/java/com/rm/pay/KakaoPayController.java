package com.rm.pay;

import com.rm.user.SiteUserRequest;
import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class KakaoPayController {

    private final KakaoPayService kakaoPayService;
    private final UserService userService;



    /**
     * 결제요청
     */
    @PostMapping("/ready")
    public KakaoReadyResponse readyToKakaoPay() {

        return kakaoPayService.kakaoPayReady();
    }

    @GetMapping("/success")
    public ResponseEntity afterPayRequest(@RequestParam("pg_token") String pgToken, Principal principal) {
        SiteUserResponse user= userService.findUserByUserID(principal.getName());


        KakaoApproveResponse kakaoApprove = kakaoPayService.approveResponse(pgToken);
//        kakaoApprove.setId(1L);
        System.out.println(kakaoApprove.getPartner_order_id()+"!!!!!!!!!!!!!!!!!");
        kakaoPayService.savePayRecord(kakaoApprove,user.getId());


        return new ResponseEntity<>(kakaoApprove, HttpStatus.OK);
    }
    /**
     * 결제 진행 중 취소
     */
//    @GetMapping("/cancel")
//    public void cancel() {
//
//        throw new BusinessLogicException(ExceptionCode.PAY_CANCEL);
//    }
//
//    /**
//     * 결제 실패
//     */
//    @GetMapping("/fail")
//    public void fail() {
//
//        throw new BusinessLogicException(ExceptionCode.PAY_FAILED);
//    }
}