package com.rm.subscribe;

import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

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

        SiteUserResponse user = userService.findUserByUserID(principal.getName());
        System.out.println(user.getId());
        subscribeRequest.setSiteUserId(user.getId());
        subscribeService.makeSubscribe(subscribeRequest);

        return "main";
    }

}
