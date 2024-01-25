package com.rm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
    @PostMapping("/login")
    public String login(@RequestBody SiteUser user) {
        SiteUser foundUser = userService.loginUser(user.getUsername(), user.getPassword());

        if (foundUser != null) {
            return "Login successful!";
        } else {
            return "Login failed!";
        }
    }
}
