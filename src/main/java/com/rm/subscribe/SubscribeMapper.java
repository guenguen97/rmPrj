package com.rm.subscribe;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SubscribeMapper {
    void save(SubscribeRequest subscribeRequest);
}
