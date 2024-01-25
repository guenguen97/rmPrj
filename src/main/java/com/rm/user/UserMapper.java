package com.rm.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {


        SiteUser getUserByUsernameAndPassword(String username, String password);

        List<SiteUser> getUser();

        void save(SiteUser user);

}
