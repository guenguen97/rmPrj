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
        System.out.println(userService.getUsers().get(0).getPassword());


        return "test";
    }

    @GetMapping("/signUpPage")
    public String signUp(){

        return "signUp";
    }



    @PostMapping("/signUp")
    public String signUp( @RequestParam(value = "userName")String username,
                          @RequestParam(value = "password1") String password1,
                          @RequestParam(value = "email") String email){
        userService.create(username,password1,email);

        return "main";
    }
}
