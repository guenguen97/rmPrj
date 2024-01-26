package com.rm.user;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SiteUser {


    private Long id;

    private String userId;

    private String userPw;

    private String userName;

    private String email;



    private LocalDateTime createDate;
}
