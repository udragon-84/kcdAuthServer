package com.kcd.service.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Slf4j
@Component
public class OAuth2LoginFailHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        // 여기에 로그인 실패 후 처리할 내용을 작성하기!
        log.info("OAuth2LoginFailHandler.onAuthenticationFailure Kakao oAuth2 Failed");
        response.sendRedirect("/authFail");
        super.onAuthenticationFailure(request, response, exception);
    }

}
