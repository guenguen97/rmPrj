//package com.rm.kakaoPay;
//
//import com.rm.kakaoPay.KakaoPayReadyVO;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.java.Log;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestClientException;
//import org.springframework.web.client.RestTemplate;
//
//import java.net.URI;
//import java.net.URISyntaxException;
//
//@Service
//@Log
//@RequiredArgsConstructor
//@Transactional
//
//public class KakaoPay {
//    @Value("${kakao-admin-key}")
//    private String key;
//    private static final String HOST = "https://kapi.kakao.com";
//
//
//
//    private  KakaoPayReadyVO kakaoPayReadyVO;
//
//
//    private KakaoPayApprovalVO kakaoPayApprovalVO;
//
//
//
//    public String kakaoPayReady() {
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 서버로 요청할 Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "KakaoAK " +key);
//        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
//        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
//
//        // 서버로 요청할 Body
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//        params.add("cid", "TC0ONETIME");
//        params.add("partner_order_id", "1001");
//        params.add("partner_user_id", "gorany");
//        params.add("item_name", "갤럭시S9");
//        params.add("quantity", "1");
//        params.add("total_amount", "2100");
//        params.add("tax_free_amount", "100");
//        params.add("approval_url", "http://localhost:8090/kakaoPaySuccess");
//        params.add("cancel_url", "http://localhost:8090/kakaoPayCancel");
//        params.add("fail_url", "http://localhost:8090/kakaoPaySuccessFail");
//
////        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//
//
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//
//        kakaoPayReadyVO = restTemplate.postForObject(
//                "https://kapi.kakao.com/v1/payment/ready",
//                requestEntity,
//                kakaoPayReady.class);
//
//        return kakaoReady;
////        try {
////            kakaoPayReadyVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/ready"), body, KakaoPayReadyVO.class);
////
////            log.info("" + kakaoPayReadyVO);
////
////            return kakaoPayReadyVO.getNext_redirect_pc_url();
////
////        } catch (RestClientException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        } catch (URISyntaxException e) {
////            // TODO Auto-generated catch block
////            e.printStackTrace();
////        }
////
////        return "/pay";
//
//    }
//
//    public KakaoPayApprovalVO kakaoPayInfo(String pg_token) {
//
//        log.info("KakaoPayInfoVO............................................");
//        log.info("-----------------------------");
//
//        RestTemplate restTemplate = new RestTemplate();
//
//        // 서버로 요청할 Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "KakaoAK " + key);
//        headers.add("Accept", MediaType.APPLICATION_JSON_UTF8_VALUE);
//        headers.add("Content-Type", MediaType.APPLICATION_FORM_URLENCODED_VALUE + ";charset=UTF-8");
//
//        // 서버로 요청할 Body
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
//        params.add("cid", "TC0ONETIME");
//        params.add("tid", kakaoPayReadyVO.getTid());
//        params.add("partner_order_id", "1001");
//        params.add("partner_user_id", "gorany");
//        params.add("pg_token", pg_token);
//        params.add("total_amount", "2100");
//
//        HttpEntity<MultiValueMap<String, String>> body = new HttpEntity<MultiValueMap<String, String>>(params, headers);
//
//        try {
//            kakaoPayApprovalVO = restTemplate.postForObject(new URI(HOST + "/v1/payment/approve"), body, KakaoPayApprovalVO.class);
//            log.info("" + kakaoPayApprovalVO);
//
//            return kakaoPayApprovalVO;
//
//        } catch (RestClientException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (URISyntaxException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        return null;
//    }
//
//}
//
