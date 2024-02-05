package com.rm.util;


import com.rm.subscribe.SubscribeResponse;
import com.rm.subscribe.SubscribeService;
import com.rm.user.SiteUserResponse;
import com.rm.user.UserService;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;


//네비바 에서 쓰이는 정보들은 거의 모든 html 에서 쓰여서 이곳에서 바로바로 정보 갖고 오려고
// rq 를 만듬
@Component
//@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Getter
@RequestScope
public class Rq {

    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final HttpSession session;
    private SiteUserResponse loginUser =null;
    private UserService userService;
    private SubscribeService subscribeService;

    private User user;



    public Rq(UserService userService , SubscribeService subscribeService) {
        ServletRequestAttributes sessionAttributes = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes()));
        HttpServletRequest request = sessionAttributes.getRequest();
        HttpServletResponse response = sessionAttributes.getResponse();
        this.request = request;
        this.response = response;
        this.session = request.getSession();
        this.userService=userService;
        this.subscribeService=subscribeService;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//

        if (authentication.getPrincipal() instanceof User) {
            this.user = (User) authentication.getPrincipal();
        } else {
            this.user= null;
        }

    }



    public boolean isLogin() {
        return user != null;
    }

    public boolean isLogout() {
        return !isLogin();
    }


    private String getLoginedMemberLoginID() {
        if (isLogout()) return null;



        return user.getUsername();
    }
    public SiteUserResponse getUser() {
        if (isLogout()) {
            return null;
        }
        if(loginUser ==null) {
            loginUser = userService.findUserByLoginID(getLoginedMemberLoginID());


        }


        return loginUser;
    }

    public SubscribeResponse getSubscribe() {
        SiteUserResponse loginUser=getUser();

        return subscribeService.getSubscribeByUserId(loginUser.getId());

    }

    public int countSubscribe(){
        SiteUserResponse loginUser=getUser();

      return subscribeService.countSubscribeByUserId(loginUser.getId());


    }



}





