package com.kcd.service.otp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "kcd opt Domain", name = "KcdOptDto")
public class KcdOptDto {
    @Schema(description = "회원 핸드폰 번호", example = "010-7318-7777")
    @Pattern(
            regexp = "^(010)(\\d{3,4})(\\d{4})$|^(010)-(\\d{3,4})-(\\d{4})$",
            message = "핸드폰번호 형식을 올바르게 입력하여 주십시요. ex(010-0000-0000, 01000000000, 010-000-0000, 0100000000)"
    )
    private String mobile;  // 휴대전화번호 점유인증 하는 경우

    @Schema(description = "회원 이메일", example = "youzang7@gmail.com")
    @Email(message = "이메일 형식을 올바르게 입력하여 주십시요.")
    private String email;   // 이메일로 점유인증 하는 경우
}