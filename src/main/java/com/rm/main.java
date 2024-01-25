package com.rm;


import com.rm.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class main {

    @Autowired
    private UserService userService;
    @GetMapping("/")
    @ResponseBody
    public String main(){
        System.out.println(userService.loginUser("asd","asd"));

        System.out.println(userService.getUsers().size());
        return "hi";
    }


}
