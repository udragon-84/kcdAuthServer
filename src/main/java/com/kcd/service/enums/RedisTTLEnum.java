package com.kcd.service.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.concurrent.TimeUnit;

@Getter
@AllArgsConstructor
public enum RedisTTLEnum {
    OTP_VERIFICATION(3, TimeUnit.MINUTES, "OTP 검증용 TTL 3분"),
    SESSION_EXPIRE(30, TimeUnit.MINUTES, "세션 만료 시간 30분"),
    CACHE_DEFAULT(60, TimeUnit.MINUTES, "캐시 기본 TTL 60분");
    private final long duration;
    private final TimeUnit timeUnit;
    private final String description;
}
