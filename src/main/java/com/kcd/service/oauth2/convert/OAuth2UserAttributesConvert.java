package com.kcd.service.oauth2.convert;
import com.kcd.service.oauth2.dto.KakaoOAuth2ResponseDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;
import java.util.Objects;

public class OAuth2UserAttributesConvert {

    public static KakaoOAuth2ResponseDto convert(Map<String, Object> oAuth2UserAttributes) {
        if (Objects.isNull(oAuth2UserAttributes))
            return new KakaoOAuth2ResponseDto();
        Map<String, Object> propertieMap = (Map<String, Object>) oAuth2UserAttributes.get("properties");
        return KakaoOAuth2ResponseDto.builder()
                .id(Long.parseLong(oAuth2UserAttributes.get("id").toString()))
                .connectedAt(LocalDateTime.ofInstant(
                        Instant.parse(oAuth2UserAttributes.get("connected_at").toString()),
                        ZoneId.systemDefault() // 시스템의 현재 시간대에 맞추어 변환
                ))
                .nickName(propertieMap.get("nickname").toString())
                .email(oAuth2UserAttributes.getOrDefault("email", "youzang7@naver.com").toString()) // 현재 과제에서는 해당 값을 넘겨줄 수 없음
                .phoneNumber(oAuth2UserAttributes.getOrDefault("phone_number", "010-1234-5678").toString()) // 현재 과제에서는 해당 값을 넘겨줄 수 없음
                .build();
    }

}