package com.rm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public SiteUser loginUser(String username, String password) {
        return userMapper.getUserByUsernameAndPassword(username, password);
    }

    public List<SiteUser> getUsers() {
        return userMapper.getUser();
    }

    public SiteUser create(String userId,String password, String userName ,String email  ){
        SiteUser user =new SiteUser();
        user.setUserId(userId);
        user.setUserName(userName);

        user.setEmail(email);
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setUserPw(passwordEncoder.encode(password));
        user.setCreateDate(LocalDateTime.now());
        userMapper.save(user);

        return user;



    }
}
