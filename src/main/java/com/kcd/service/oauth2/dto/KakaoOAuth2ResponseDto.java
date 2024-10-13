package com.kcd.service.oauth2.dto;

import com.kcd.common.encrypt.Aes128Encryptor;
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
public class KakaoOAuth2ResponseDto extends Aes128Encryptor {
    @Schema(description = "Kakao oAuth2 응답 id", example = "356324343")
    private Long id;

    @Schema(description = "Kakao oAuth2 응답시간", example = "2024-10-11T11:55:56")
    private LocalDateTime connectedAt;

    @Schema(description = "Kakao oAuth2 사용자정보(닉네임)", example = "유창근")
    private String nickName;

    @Schema(description = "Kakao oAuth2 사용자정보(이메일)", example = "youzang7@naver.com")
    private String email; //현재 과제에서는 이메일 정보와 핸드폰 번호를 읽어올 수 없음

    @Schema(description = "Kakao oAuth2 사용자정보(핸드폰)", example = "010-1234-5678")
    private String phoneNumber; //현재 과제에서는 이메일 정보와 핸드폰 번호를 읽어올 수 없음

    @Override
    public void encryptFields() {
        this.processFields(true);
    }

    @Override
    public void decryptFields() {
        this.processFields(false);
    }

    private void processFields(boolean isEncrypt) {
        this.email = this.processField(this.email, isEncrypt);
        this.phoneNumber = this.processField(this.phoneNumber, isEncrypt);
    }
}
