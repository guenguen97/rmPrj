package com.rm.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }




    public SiteUserResponse findUserByLoginID(String loginID){
        return userMapper.getByLoginID(loginID);
    }

    public Long create(SiteUserRequest user  ){

        userMapper.save(user);

        return user.getId();

    }

    @Transactional
    public Long updateUser(final SiteUserRequest params) {

        userMapper.update(params);
        System.out.println("업데이트 끝!!!!!!!!!!!!!!!");
        return params.getId();
    }

    public int countSiteUserByLoginID(String loginID) {
        return userMapper.getSiteUserByLoginID(loginID);
    }
}
