package com.kcd.service.oauth2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Kakao oAuth2 응답 도메인", name = "KakaoOAuth2ResponseDto")
public class KakaoOAuth2ResponseDto {
    @Schema(description = "Kakao oAuth2 응답 id", example = "356324343")
    private Long id;
    @Schema(description = "Kakao oAuth2 응답시간", example = "2024-10-11T11:55:56")
    private LocalDateTime connectedAt;
    @Schema(description = "Kakao oAuth2 사용자정보(닉네임)", example = "유창근")
    private String nickName;
}
