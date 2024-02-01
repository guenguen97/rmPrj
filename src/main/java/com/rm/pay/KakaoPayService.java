package com.rm.pay;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rm.subscribe.SubscribeRequest;
import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoPayService {
    private final UserService userService;

    private  final PayMapper payMapper;
    static final String cid = "TC0ONETIME"; // 가맹점 테스트 코드
    private String adminKey;

    @Value("${kakao-admin-key}")
    String key;
    private KakaoReadyResponse kakaoReady;


    //여기 매개변수에 구독요청 클래스 변수들을 추가 해야됨
    public void kakaoPayReady(HttpServletResponse response , SubscribeRequest subscribeRequest,
                              Principal principal) throws IOException {
        String itemName="";
        SiteUserResponse user=userService.findUserByLoginID(principal.getName());
        System.out.println(subscribeRequest.getRank()+"!!!!!!!!!!!!");
        //연장 신청시에는 기간만 받아오기떔에 그거 예외 처리
        if( subscribeRequest.getRank()==null){
            itemName="연장 신청";
        }else {
        itemName=subscribeRequest.getRank();}
        // 카카오페이 요청 양식
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", "RmSoft");
        parameters.add("partner_user_id", user.getUserName());
        parameters.add("item_name", itemName); //구독한 등급 이름
        parameters.add("quantity", ""+subscribeRequest.getPeriod()); // 구독 신청한 기간
        parameters.add("total_amount", "10000"); //결제 금액은 고정이기에 일단 만원
        parameters.add("vat_amount", "100");
        parameters.add("tax_free_amount", "100");
        parameters.add("approval_url", "http://localhost:8090/payment/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8090/payment/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8090/payment/fail"); // 실패 시 redirect url
        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        kakaoReady = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoReadyResponse.class);

        response.sendRedirect(kakaoReady.getNext_redirect_pc_url());

    }
    public KakaoApproveResponse approveResponse(String pgToken ,Principal principal) {
        SiteUserResponse user=userService.findUserByLoginID(principal.getName());
        // 카카오 요청
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("tid", kakaoReady.getTid());
        parameters.add("partner_order_id", "RmSoft");
        parameters.add("partner_user_id",user.getUserName());
        parameters.add("pg_token", pgToken);

        // 파라미터, 헤더
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());

        // 외부에 보낼 url
        RestTemplate restTemplate = new RestTemplate();

        KakaoApproveResponse approveResponse = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoApproveResponse.class);

        return approveResponse;
    }
    /**
     * 카카오 요구 헤더값
     */
    private HttpHeaders getHeaders() {
        HttpHeaders httpHeaders = new HttpHeaders();
        String admin_Key=key;
        String auth = "KakaoAK " + admin_Key;

        httpHeaders.set("Authorization", auth);
        httpHeaders.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        return httpHeaders;
    }

    public void savePayRecord(KakaoApproveResponse kakaoApproveResponse,Long siteUserId){
        payMapper.save(kakaoApproveResponse, siteUserId );

    }
}