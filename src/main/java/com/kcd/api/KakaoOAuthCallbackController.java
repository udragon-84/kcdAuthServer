package com.kcd.api;

import com.kcd.service.oauth2.dto.KakaoOAuth2ResponseDto;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Map;
import java.util.Optional;

@Slf4j
@Controller
@Tag(name = "kakao 인증 Api 명세", description = "kakao 인증 Controller")
public class KakaoOAuthCallbackController {

    @GetMapping("/signup")
    public String kakaoAuthCallback(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response) {
        Map<String, Object> kakaoAccountMap = oAuth2User.getAttributes();
        KakaoOAuth2ResponseDto kakaoOAuth2ResponseDto = Optional.of(kakaoAccountMap.get("kakaoOAuth2ResponseDto"))
                .map(map -> (KakaoOAuth2ResponseDto)map)
                .orElseThrow(() -> new IllegalArgumentException("KakaoOAuth2ResponseDto not found in attributes"));
        log.info("KakaoOAuthCallbackController.kakaoAuthCallback: {}", kakaoOAuth2ResponseDto);
        return kakaoOAuth2ResponseDto.toString();
    }

}
