package com.rm.pay;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PayMapper {




    void save(@Param("kakaoApproveResponse") KakaoApproveResponse kakaoApproveResponse,  @Param("siteUserId")  Long siteUserId);
}
