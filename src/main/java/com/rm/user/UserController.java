package com.rm.user;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

        return "login";
    }



    @PostMapping("/signUp")
    public String signUp(SiteUserRequest user){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPw(passwordEncoder.encode(user.getUserPw()));
        userService.create(user);
        System.out.println(user.getId());

        return "main";
    }

    @GetMapping("/siteUser-count")
    @ResponseBody
    public int countMemberByLoginId(@RequestParam (name = "userID") final String userID) {
        System.out.println(userID+"!!!!!!!!!!!!!!!!!!!!!!!!");
        return userService.countSiteUserByUserID(userID);
    }

}
