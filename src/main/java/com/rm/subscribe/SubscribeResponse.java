package com.rm.subscribe;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.rm.user.SiteUserRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubscribeResponse {
    @JsonProperty("id")
    private Long id;
    private Long siteUserId;
    private int storageSize;
    private int period;
    private int headCount;
    private String rank;
    private String office;
    private int phoneNumber;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
}
