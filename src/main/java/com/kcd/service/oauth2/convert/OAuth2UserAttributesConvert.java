package com.kcd.service.oauth2.convert;
import com.kcd.service.oauth2.dto.KakaoOAuth2ResponseDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Map;

public class OAuth2UserAttributesConvert {

    public static KakaoOAuth2ResponseDto convert(Map<String, Object> oAuth2UserAttributes) {
        Map<String, Object> propertieMap = (Map<String, Object>) oAuth2UserAttributes.get("properties");
        return KakaoOAuth2ResponseDto.builder()
                .id(Long.parseLong(oAuth2UserAttributes.get("id").toString()))
                .connectedAt(LocalDateTime.ofInstant(
                        Instant.parse(oAuth2UserAttributes.get("connected_at").toString()),
                        ZoneId.systemDefault() // 시스템의 현재 시간대에 맞추어 변환
                ))
                .nickName(propertieMap.get("nickname").toString())
                .build();
    }

}