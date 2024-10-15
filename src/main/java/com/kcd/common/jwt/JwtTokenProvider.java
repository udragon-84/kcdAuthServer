package com.kcd.common.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import java.security.Key;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.Map;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${JWT_TOKEN_SECRET}")
    private String jwtTokenSecret;

    // JWT 토큰 생성
    public String createToken(Map<String, Object> claimsMaps) {
        Claims claims = Jwts.claims().setSubject(claimsMaps.get("id").toString());
        claims.putAll(claimsMaps);

        Date now = new Date();
        Date validity = new Date(now.getTime() + 20 * 60 * 1000); // 20분 유효

        // SecretKeySpec을 사용하여 Key 객체 생성
        Key key = new SecretKeySpec(jwtTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256) // Key 객체를 사용하여 서명
                .compact();
    }

    // JWT 토큰에서 사용자 ID 추출
    public String getSubject(String token) {
        Key key = new SecretKeySpec(jwtTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parserBuilder()               // 새로운 파서 빌더 사용
                .setSigningKey(key)               // 서명 검증을 위한 키 설정
                .build()                          // 파서 빌드
                .parseClaimsJws(token)            // 토큰 파싱 및 서명 검증
                .getBody()
                .getSubject();                    // 사용자 ID 추출
    }

    // JWT 토큰에서 저장된 사용자 전체 값 추출 usage: getAllClaims(mobile).get("mobile");
    public Claims getAllClaims(String token) {
        Key key = new SecretKeySpec(jwtTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // 토큰 파싱 및 서명 검증
                .getBody();// 모든 Claims 반환
    }

    public boolean validationToken(String token) {
        try {
            // SecretKeySpec을 사용하여 Key 객체 생성
            Key key = new SecretKeySpec(jwtTokenSecret.getBytes(), SignatureAlgorithm.HS256.getJcaName());
            Jwts.parserBuilder().setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch(Exception e) {
            log.error("JwtTokenProvider.validationToken Exception", e);
            return false;
        }
    }

}
