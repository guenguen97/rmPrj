package com.rm.subscribe;


import com.rm.user.SiteUserRequest;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SubscribeRequest {

    private Long id;
    private Long siteUserId;
    private int storageSize;
    private int period;
    private int headCount;
    private String rank;
    private String office;
    private int phoneNumber;
    private LocalDateTime createDate;
}
