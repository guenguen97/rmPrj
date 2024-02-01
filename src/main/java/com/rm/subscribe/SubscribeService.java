package com.rm.subscribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SubscribeService {
    @Autowired
    SubscribeMapper subscribeMapper;

    // 구독 신청 정보 저장
    public void makeSubscribe(SubscribeRequest subscribeRequest){
        subscribeMapper.save(subscribeRequest);
    }

    //유저.id 로 구독한 정보를 찾기
    public SubscribeResponse getSubscribeByUserId(Long userId){

        return subscribeMapper.getSubscribeByUserId(userId);
    }


    //이미 구독한 신청한 유저 일시 중복으로 신청은 안되고 기간 연장만 할 수 있게
    public int countSubscribeByUserId(Long userId){
        return subscribeMapper.countSubscribeByUserId(userId);
    }

    public String updatePeriod(SubscribeRequest params) {


        return subscribeMapper.updatePeriod(params);
    }
}
