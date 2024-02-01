package com.rm.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SiteUserResponse {


    private Long id;
    private String loginID;
    private String userPw;
    private String userName;
    private String email;
    private Boolean ticket;  //회원 정지 여부
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
