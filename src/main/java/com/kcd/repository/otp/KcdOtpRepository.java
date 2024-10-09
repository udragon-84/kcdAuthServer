package com.kcd.repository.otp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;
import java.util.concurrent.TimeUnit;

@Repository
public class KcdOtpRepository {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public void saveOtp(String id, String otp, long duration, TimeUnit timeUnit) {
        this.stringRedisTemplate.opsForValue().set(id, otp, duration, timeUnit);
    }

    public String getOtp(String id) {
        return this.stringRedisTemplate.opsForValue().get(id);
    }

    public boolean deleteOtp(String id) {
        return Boolean.TRUE.equals(this.stringRedisTemplate.delete(id));
    }
}
