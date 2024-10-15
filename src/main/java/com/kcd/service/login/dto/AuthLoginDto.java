package com.kcd.service.login.dto;

import com.kcd.common.encrypt.Aes128Encryptor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "kcd 유저 로그인 정보 Domain", name = "UserAuthLoginDto")
public class AuthLoginDto extends Aes128Encryptor {

    @Schema(description = "회원 Id", example = "youzang7@naver.com || 010-1234-5678")
    @NotBlank(message = "회원 이름을 입력하여 주십시요.")
    private String loginId; // 이메일 또는 휴대전화번호

    @Schema(description = "회원 비밀번호")
    @NotBlank(message = "회원 비밀번호를 입력하여 주십시요.")
    private String password;

    @Override
    public void encryptFields() {
        this.processFields(true);
    }

    @Override
    public void decryptFields() {
        this.processFields(false);
    }

    private void processFields(boolean isEncrypt) {
        this.loginId = this.processField(this.loginId, isEncrypt);
        this.password = this.processField(this.password, isEncrypt);
    }
}
