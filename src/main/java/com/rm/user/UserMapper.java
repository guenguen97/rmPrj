package com.rm.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {


        SiteUserResponse getByUserID(String userID);
        List<SiteUserRequest> getUser();

        void save(SiteUserRequest user);

}
