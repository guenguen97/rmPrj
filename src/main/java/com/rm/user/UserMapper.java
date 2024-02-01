package com.rm.user;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {


        SiteUserResponse getByLoginID(String loginID);

        void save(SiteUserRequest user);


        void update(SiteUserRequest params);
        int getSiteUserByLoginID(String loginID);


}
