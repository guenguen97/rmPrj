package com.rm.subscribe;

import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SubscribeController {

    @Autowired
    private SubscribeService subscribeService;

    @Autowired
    private UserService userService;

    @GetMapping("/subscribe")
    public String subscribe(){


        return "subscribe";
    }

    //구독 정보 저장하기
    @PostMapping("/post/subscribe")
    public String requestSubscribe(SubscribeRequest subscribeRequest, Principal principal){

        SiteUserResponse user = userService.findUserByLoginID(principal.getName());
        System.out.println(user.getId());
        subscribeRequest.setSiteUserId(user.getId());
        subscribeService.makeSubscribe(subscribeRequest);

        return "main";
    }


    @GetMapping("/updateSubscribe")
    public String updateSubscribe(){


        return "updateSubscribe";
    }

    //구독 기간 연장하기
    @PatchMapping("/subscribe/period/{id}")
    @ResponseBody
    public void updatePeriod(@PathVariable(name = "id") final Long id,@RequestBody SubscribeRequest params){
        params.setId(id);
        //기존에 있던 구독 기간 기록 갖고 오기
        SubscribeResponse originSub=subscribeService.getSubscribeByUserId(id);

        //기존에 있던 구독 기간이랑 요청 들어온 구독 연장 기간 더하기
        params.setPeriod(params.getPeriod()+originSub.getPeriod());


         subscribeService.updatePeriod(params);


    }

    @GetMapping("/currentSubscribePage")
    public String currentSubscribePage(){

        return "currentSubscribe";
    }

    //현재 구독한 정보 갖고 오기
    @GetMapping("/currentSubscribe")
    @ResponseBody
    public SubscribeResponse currentSubscribe(Principal principal) {


            SiteUserResponse user = userService.findUserByLoginID(principal.getName());
            SubscribeResponse subscribeResponse = subscribeService.getSubscribeByUserId(user.getId());


            return subscribeResponse;

    }

}
