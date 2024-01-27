package com.rm.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserSecurityService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userID) throws UsernameNotFoundException {
        System.out.println(userID+"asdasd!!!!!!!!!");
        System.out.println("로그인 검사중!!!!!!!!!");
       SiteUserResponse _siteUser = this.userService.findUserByUserID(userID);
        if (_siteUser==null) {
            throw new UsernameNotFoundException("사용자를 찾을수 없습니다.");
        }
        SiteUserResponse siteUser = _siteUser;
        System.out.println(siteUser.getUserID()+"!!!!!!!!!!!!!!!");
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(userID)) {
            authorities.add(new SimpleGrantedAuthority(UserRole.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserRole.USER.getValue()));
        }
        return new User(siteUser.getUserID(), siteUser.getUserPw(), authorities);
    }
}
