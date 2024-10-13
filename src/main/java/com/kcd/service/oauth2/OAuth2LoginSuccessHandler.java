package com.kcd.service.oauth2;

import com.kcd.common.session.SessionCookieManager;
import com.kcd.service.oauth2.dto.KakaoOAuth2ResponseDto;
import com.kcd.service.user.UserService;
import com.kcd.service.user.dto.UserDto;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserService userService;

    @Autowired
    private SessionCookieManager sessionCookieManager;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        KakaoOAuth2ResponseDto kakaoOAuth2ResponseDto = (KakaoOAuth2ResponseDto)attributes.get("kakaoOAuth2ResponseDto");
        log.info("KakaoOAuth2ResponseDto encrypt: {}", kakaoOAuth2ResponseDto);

        // 1. 회원 조회 (DB 에는 암호화된 값 AES-128 값으로 저장되어 있으니 암호화된 값으로 찾아와야 한다)
        UserDto userDto = this.userService.findByEmailOrMobile(kakaoOAuth2ResponseDto.getEmail(), kakaoOAuth2ResponseDto.getPhoneNumber());
        // 2. 회원이 있을 경우 로그인 처리 (jwt 토큰 기반 세션 생성 및 쿠키 생성 그리고 메인 페이지 이동)
        if (Objects.nonNull(userDto) && this.isUser(oAuth2User)) {
            this.sessionCookieManager.processSessionAndCookie(String.valueOf(userDto.getId()), request, response);
            getRedirectStrategy().sendRedirect(request, response, "/defaultMain.html");
        } else {
            // 3. 회원이 없는 경우 회원가입 페이지로 이동
            getRedirectStrategy().sendRedirect(request, response, "/signUp.html");
        }
        super.onAuthenticationSuccess(request, response, authentication);
    }

    // 스프링 시큐리티 ROLE_USER 권한을 가지고 있는지 확인
    private boolean isUser(OAuth2User oAuth2User) {
        return oAuth2User.getAuthorities()
                .stream()
                .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));
    }
}
