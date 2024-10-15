package com.kcd.service.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kcd.common.encrypt.Aes128Encryptor;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.time.LocalDateTime;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "kcd 유저 정보 Domain", name = "UserDto")
public class UserDto extends Aes128Encryptor {

    @Schema(description = "kcd 회원 관리 내부 id", example = "1")
    private Long id;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 패스워드 필드를 JSON 응답에서 제외
    @Schema(description = "회원 패스워드", example = "*********")
    @NotBlank(message = "회원 비밀번호를 입력하여 주십시요.")
    private String password;   // 이메일로 점유인증 하는 경우

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

    @Schema(description = "회원등록 일자", example = "2024-10-11T11:55:56")
    private LocalDateTime createdAt;  // 등록일

    @Override
    public void encryptFields() {
        this.processFields(true);
    }

    @Override
    public void decryptFields() {
        this.processFields(false);
    }

    private void processFields(boolean isEncrypt) {
        this.mobile = this.processField(this.mobile, isEncrypt);
        this.email = this.processField(this.email, isEncrypt);
        this.password = this.processField(this.password, isEncrypt);
        this.birthday = this.processField(this.birthday, isEncrypt);
        this.rrn7th = this.processField(this.rrn7th, isEncrypt);
        this.gender = this.processField(this.gender, isEncrypt);
        this.nationality = this.processField(this.nationality, isEncrypt);
    }
}
