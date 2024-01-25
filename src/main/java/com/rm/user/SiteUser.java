package com.rm.user;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUser {


    private Long id;

    private String username;

    private String password;

    private String email;
}
