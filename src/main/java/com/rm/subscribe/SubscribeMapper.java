package com.rm.subscribe;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscribeMapper {
    void save(SubscribeRequest subscribeRequest);

    SubscribeResponse getSubscribeByUserId(Long userId);

    int countSubscribeByUserId(Long userId);

    void updatePeriod(SubscribeRequest params);
}
