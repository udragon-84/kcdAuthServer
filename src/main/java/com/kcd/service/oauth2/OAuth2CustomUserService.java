package com.kcd.service.oauth2;

import com.kcd.common.exception.AuthException;
import com.kcd.service.oauth2.convert.OAuth2UserAttributesConvert;
import com.kcd.service.oauth2.dto.KakaoOAuth2ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class OAuth2CustomUserService extends DefaultOAuth2UserService {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> oAuth2UserAttributes = oAuth2User.getAttributes();

        KakaoOAuth2ResponseDto kakaoOAuth2ResponseDto = Optional.ofNullable(oAuth2UserAttributes)
                .map(OAuth2UserAttributesConvert::convert)
                .stream()
                .peek(KakaoOAuth2ResponseDto::encryptFields) //받아온 값은 바로 암호화 처리
                .findAny()
                .orElseThrow(() -> new AuthException("Kakao oAuth2 유저 정보가 없습니다."));

        Map<String, Object> newAttributes = new HashMap<>();
        newAttributes.put("kakaoOAuth2ResponseDto", kakaoOAuth2ResponseDto);
        newAttributes.put("id", kakaoOAuth2ResponseDto.getId());
        newAttributes.put("connected_at", kakaoOAuth2ResponseDto.getConnectedAt());

        String role = kakaoOAuth2ResponseDto.getEmail().isBlank() && kakaoOAuth2ResponseDto.getPhoneNumber().isBlank() ?
                "ROLE_ANONYMOUS" : "ROLE_USER";

        return new DefaultOAuth2User(
                List.of(new SimpleGrantedAuthority(role)),
                newAttributes,
                "id"
        );
    }

}
