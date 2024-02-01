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
    public String myPage(){
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
    public int countMemberByLoginId(@RequestParam (name = "loginID") final String loginID) {
        System.out.println(loginID+"!!!!!!!!!!!!!!!!!!!!!!!!");
        return userService.countSiteUserByLoginID(loginID);
    }


    @GetMapping("/changeInfor")
    public String changeInfor(){
        return "changeInfor";
    }

    @PatchMapping("/information/{id}")
    @ResponseBody
    public Long updateMember(@PathVariable(name = "id") final Long id, @RequestBody final SiteUserRequest params) {
        System.out.println("회원 정보 수정 시작!!!!!!!!!!!!!!");
        System.out.println(params.getUserName());
        params.setId(id);
        System.out.println(params.getId());
        System.out.println(params.getEmail());
        return userService.updateUser(params);
    }

}
