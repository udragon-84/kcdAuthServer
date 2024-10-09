package com.kcd.service.otp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "KCD OPT 인증 Domain", name = "KcdOptAuthDto")
public class KcdOtpAuthDto {

    @Schema(description = "회원 이름", example = "유창근")
    @NotBlank(message = "회원 이름을 입력하여 주십시요.")
    private String name;    // 이름

    @Schema(description = "회원 핸드폰 번호", example = "010-7318-7777")
    @Pattern(
            regexp = "^(010)(\\d{3,4})(\\d{4})$|^(010)-(\\d{3,4})-(\\d{4})$",
            message = "핸드폰번호 형식을 올바르게 입력하여 주십시요. ex(010-0000-0000, 01000000000, 010-000-0000, 0100000000)"
    )
    private String mobile;  // 휴대전화번호 점유인증 하는 경우

    @Schema(description = "회원 이메일", example = "youzang7@gmail.com")
    @Email(message = "이메일 형식을 올바르게 입력하여 주십시요.")
    private String email;   // 이메일로 점유인증 하는 경우

    @Schema(description = "생년월일", example = "841021")
    @NotBlank(message = "생년월일을 입력하여 주십시요.")
    private String birthday; // 생년월일

    @Schema(description = "주민번호 뒷 7자리", example = "1015724")
    @NotBlank(message = "주민번호 뒷 7자리를 입력하여 주십시요.")
    private String rrn7th; // 주민번호 뒷 7자리

    @Schema(description = "성별 (남 Or 여)", example = "남")
    @NotBlank(message = "성별을 입력하여 주십시요.")
    private String gender; // 성별

    @Schema(description = "국적", example = "대한민국")
    @NotBlank(message = "국적을 입력하여 주십시요.")
    private String nationality; //국적

    @Schema(description = "통신사", example = "SK")
    @NotBlank(message = "통신사를 입력하여 주십시요.")
    private String tsp; //통신사

    @Schema(description = "본인인증 기관", example = "KAKAO")
    @NotBlank(message = "본인인증 기관을 입력하여 주십시요.")
    private String provider; // 본인인증 기관

}
