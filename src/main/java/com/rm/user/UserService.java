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



    public List<SiteUserRequest> getUsers() {
        return userMapper.getUser();
    }

    public SiteUserResponse findUserByUserID(String userID){
        return userMapper.getByUserID(userID);
    }

    public Long create(SiteUserRequest user  ){

        userMapper.save(user);

        return user.getId();



    }

    public int countSiteUserByUserID(String userID) {
        return userMapper.getSiteUserByUserID(userID);
    }
}
