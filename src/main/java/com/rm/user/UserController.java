package com.rm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/user")

public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){


        return "test";
    }

    @GetMapping("/signUp-page")
    public String signUp(){

        return "signUp";
    }



    @PostMapping("/signUp")
    public String signUp( @RequestParam(value = "userId")String userId,
                          @RequestParam(value = "password1") String password1,
                          @RequestParam(value = "userName") String userName,
                          @RequestParam(value = "email") String email){
        userService.create(userId,password1,userName,email);

        return "main";
    }


}
