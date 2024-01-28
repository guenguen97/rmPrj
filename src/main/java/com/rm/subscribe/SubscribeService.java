package com.rm.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {
    @Autowired
    SubscribeMapper subscribeMapper;


    public void makeSubscribe(SubscribeRequest subscribeRequest){

        subscribeMapper.save(subscribeRequest);

    }


}
