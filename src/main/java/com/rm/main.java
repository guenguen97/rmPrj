package com.rm;


import com.rm.user.UserService;
import com.rm.util.Alert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class main {



    @Autowired
    private UserService userService;
    @GetMapping("/")
    public String main(@RequestParam(name = "alertKind", required = false) String alertKind, Model model){
        Alert alert = new Alert();

        alert.setKind(alertKind);
        model.addAttribute("alert", alert);

        return "main";
    }


}
