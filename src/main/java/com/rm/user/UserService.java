package com.rm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
