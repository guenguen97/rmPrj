package com.rm.subscribe;

import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

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
    @PatchMapping("/subscribe/period/{id}")
    @ResponseBody
    public String updatePeriod(@PathVariable(name = "id") final Long id,@RequestBody SubscribeRequest params){
        params.setId(id);
        //기존에 있던 구독 기간 기록 갖고 오기
        SubscribeResponse originSub=subscribeService.getSubscribeByUserId(id);

        //기존에 있던 구독 기간이랑 요청 들어온 구독 연장 기간 더하기
        params.setPeriod(params.getPeriod()+originSub.getPeriod());


        return subscribeService.updatePeriod(params);


    }

}
