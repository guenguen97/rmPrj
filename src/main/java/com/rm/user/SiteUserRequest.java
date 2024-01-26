package com.rm.user;



import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SiteUserRequest {

    private Long id;
    private String userID;
    private String userPw;
    private String userName;
    private String email;
    private LocalDateTime createDate;
}
