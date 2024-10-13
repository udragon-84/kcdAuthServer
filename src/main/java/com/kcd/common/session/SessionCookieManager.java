package com.kcd.common.session;

import com.kcd.common.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SessionCookieManager {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // JWT 토큰 생성 및 세션, 쿠키 저장 처리
    public void processSessionAndCookie(String userId, HttpServletRequest request, HttpServletResponse response) {
        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(userId);
        log.info("SessionCookieManager.processSessionAndCookie jwtToekn: {}", token);

        // JWT 토큰을 세션에 저장
        HttpSession session = request.getSession();
        session.setAttribute("KCD_SESSION_TOKEN", token);  // 세션에 ID 토큰 저장

        // JWT 토큰을 쿠키에 저장
        Cookie jwtCookie = new Cookie("KCD_COOKIE_TOKEN", token);
        jwtCookie.setHttpOnly(false);  // 자바스크립트 접근 방지 (보안) true 로 할 경우 javascript 쿠키 사용 불가
        jwtCookie.setSecure(true);    // HTTPS 에서만 쿠키가 전송되도록 설정
        jwtCookie.setPath("/");       // 사이트 전체에서 쿠키 사용 가능

        // 응답에 쿠키 추가
        response.addCookie(jwtCookie);
    }
}
