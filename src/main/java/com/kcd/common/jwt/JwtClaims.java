package com.kcd.common.jwt;
import java.util.Map;

public interface JwtClaims {
    Map<String, Object> generateClaimsMap();
}
