package com.rm.kakaoPay;

import lombok.Data;

import java.util.Date;

@Data
public class KakaoPayReadyVO {

    private String tid, next_redirect_pc_url;
    private Date created_at;
}
