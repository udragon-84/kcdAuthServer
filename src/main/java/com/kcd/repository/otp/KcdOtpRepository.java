package com.kcd.repository.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class KcdOtpRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


}
