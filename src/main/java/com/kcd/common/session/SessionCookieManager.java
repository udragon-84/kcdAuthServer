package com.kcd.common.session;

import com.kcd.common.jwt.JwtClaims;
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
    public void processSessionAndCookie(JwtClaims jwtClaims, HttpServletRequest request, HttpServletResponse response) {
        // JWT 토큰 생성
        String token = jwtTokenProvider.createToken(jwtClaims.generateClaimsMap());
        log.info("SessionCookieManager.processSessionAndCookie token: {}", token);
        this.saveSession(token, request);
        this.saveCookie(token, response);
    }

    private void saveSession(String token, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("ID", token);  // 세션에 ID 토큰 저장
    }

    private void saveCookie(String token, HttpServletResponse response) {
        // JWT 토큰을 쿠키에 저장
        Cookie jwtCookie = new Cookie("ID", token);
        jwtCookie.setHttpOnly(false);  // 자바스크립트 접근 방지 (보안) true 로 할 경우 javascript 쿠키 사용 불가
        jwtCookie.setSecure(true);    // HTTPS 에서만 쿠키가 전송되도록 설정
        jwtCookie.setPath("/");       // 사이트 전체에서 쿠키 사용 가능
        // 응답에 쿠키 추가
        response.addCookie(jwtCookie);
    }

    // 로그아웃 시 세션 및 쿠키 삭제 처리
    public void invalidateSessionAndCookie(HttpServletRequest request, HttpServletResponse response) {
        // 세션 무효화
        HttpSession session = request.getSession(false);  // 세션이 없으면 null 반환
        if (session != null) {
            session.removeAttribute("ID");  // 세션에서 토큰 제거
            session.invalidate();  // 세션 무효화
            log.info("SessionCookieManager.invalidateSessionAndCookie: 세션 무효화 완료");
        }
        // 쿠키 삭제
        Cookie jwtCookie = new Cookie("ID", null);
        jwtCookie.setHttpOnly(false);
        jwtCookie.setSecure(true);
        jwtCookie.setPath("/");
        jwtCookie.setMaxAge(0);
        response.addCookie(jwtCookie);
        log.info("SessionCookieManager.invalidateSessionAndCookie: 쿠키 삭제 완료");
    }
}
