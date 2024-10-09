package com.kcd.api;

import com.kcd.api.response.KcdAuthResponse;
import com.kcd.common.exception.AuthException;
import com.kcd.service.otp.KcdOtpAuthService;
import com.kcd.service.otp.dto.KcdOtpAuthDto;
import com.kcd.service.otp.dto.KcdOtpVerficationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/otp")
@Tag(name = "kcd OTP 인증 Api 명세", description = "kcd OTP 인증 Controller")
public class KcdOtpAuthController {

    @Autowired
    private KcdOtpAuthService kcdOtpAuthService;

    @Operation(summary = "Opt 발급 Api", description = "Opt 발급 Api")
    @PostMapping("/issue")
    public KcdAuthResponse<Map<String, String>> otpIssue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Opt 발급 json 파라메터 정의",
                    required = true,
                    content = @Content(schema = @Schema(implementation = KcdOtpAuthDto.class), examples = @ExampleObject(
                            value = "{ \"name\": \"유창근\", " +
                                    "\"mobile\": \"010-1234-5678\", " +
                                    "\"email\": \"\", " +
                                    "\"birthday\": \"841021\", " +
                                    "\"rrn7th\": \"1015724\", " +
                                    "\"gender\": \"남\", " +
                                    "\"nationality\": \"대한민국\", " +
                                    "\"tsp\": \"SK\", " +
                                    "\"provider\": \"KAKAO\" }"
                    )))
            @Validated @RequestBody KcdOtpAuthDto kcdOtpAuthDto) {
        log.info("KcdOtpAuthController.issue kcdOptAuthDto: {}", kcdOtpAuthDto);
        this.parameterValidation(kcdOtpAuthDto);

        Map<String, String> otpMap = this.kcdOtpAuthService.issueOtp(kcdOtpAuthDto);
        return new KcdAuthResponse<>(otpMap);
    }

    @Operation(summary = "Opt 검증 Api", description = "Opt 검증 Api")
    @PatchMapping("/verification")
    public KcdAuthResponse<String> otpVerification(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Opt 검증 json 파라메터 정의",
            required = true,
            content = @Content(schema = @Schema(implementation = KcdOtpVerficationDto.class), examples = @ExampleObject(
                    value = "{ \"id\": \"010-1234-5678\", " +
                            "\"otp\": \"710430\" }"
            )))
            @Validated @RequestBody KcdOtpVerficationDto kcdOtpVerficationDto) {
        log.info("KcdOtpAuthController.otpVerification kcdOptVerficationDto: {}", kcdOtpVerficationDto);
        return new KcdAuthResponse<>(this.kcdOtpAuthService.verifyOtp(kcdOtpVerficationDto));
    }

    private void parameterValidation(KcdOtpAuthDto kcdOtpAuthDto) {
        if (StringUtils.isBlank(kcdOtpAuthDto.getMobile()) && StringUtils.isBlank(kcdOtpAuthDto.getEmail()))
            throw new AuthException("핸드폰 번호나 이메일 중 하나는 반드시 입력해야 합니다.");
    }

}