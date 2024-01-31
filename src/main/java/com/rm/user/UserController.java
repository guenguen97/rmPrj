package com.rm.user;

import com.rm.util.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    Rq rq;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/test")
    @ResponseBody
    public String test(){


        return "test";
    }

    @GetMapping("/login")
    public String signUp(){

        return "login";
    }

    @GetMapping("/myPage")
    public String myPage(Principal principal, Model model){
        SiteUserResponse user=rq.getUser();
        model.addAttribute("user" ,user);
        return "myPage";
    }


    @PostMapping("/signUp" )
    public String signUp(@RequestBody SiteUserRequest user){
        System.out.println("정보 받기 성공!!!!!!!!!!");
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
