package com.kcd.service.otp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Builder
@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "KCD OTP 검증 Domain", name = "KcdOtpVerficationDto")
public class KcdOtpVerficationDto {

    @Schema(description = "otp 검증 id", example = "010-1234-5678 Or youzang7@gmail.com")
    @NotBlank(message = "회원 otp Id를 입력하여 주십시요.")
    private String id;    // otp id

    @Schema(description = "otp 값 6자리", example = "333435")
    @NotBlank(message = "otp 값 6자리를 입력하여 주십시요.")
    @Pattern(regexp = "\\d{6}", message = "opt 값은 6자리 숫자여야 합니다.")
    private String otp;    // otp value
}
