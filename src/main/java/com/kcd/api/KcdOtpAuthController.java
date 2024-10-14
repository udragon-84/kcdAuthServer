package com.kcd.api;

import com.kcd.api.response.KcdAuthResponse;
import com.kcd.common.exception.AuthException;
import com.kcd.service.oauth2.RoleService;
import com.kcd.service.otp.KcdOtpAuthService;
import com.kcd.service.otp.dto.KcdOptDto;
import com.kcd.service.otp.dto.KcdOtpVerficationDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private RoleService roleService;

    @Operation(summary = "Opt 발급 Api", description = "Opt 발급 Api")
    @PostMapping("/issue")
    public KcdAuthResponse<Map<String, String>> otpIssue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Opt 발급 json 파라메터 정의",
                    required = true,
                    content = @Content(schema = @Schema(implementation = KcdOptDto.class), examples = @ExampleObject(
                            value = "{ \"mobile\": \"\", " +
                                    "\"email\": \"youzang7@naver.com\" " + "}"
                    )))
            @Validated @RequestBody KcdOptDto kcdOptDto) {
        log.info("KcdOtpAuthController.issue kcdOptDto: {}", kcdOptDto);
        this.parameterValidation(kcdOptDto);

        Map<String, String> otpMap = this.kcdOtpAuthService.issueOtp(kcdOptDto);
        return new KcdAuthResponse<>(otpMap);
    }

    @Operation(summary = "Opt 검증 Api", description = "Opt 검증 Api")
    @PatchMapping("/verification")
    public KcdAuthResponse<String> otpVerification(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Opt 검증 json 파라메터 정의",
            required = true,
            content = @Content(schema = @Schema(implementation = KcdOtpVerficationDto.class), examples = @ExampleObject(
                    value = "{ \"id\": \"youzang7@naver.com\", " +
                            "\"otp\": \"710430\" }"
            )))
            @Validated @RequestBody KcdOtpVerficationDto kcdOtpVerficationDto, HttpServletRequest request) {
        log.info("KcdOtpAuthController.otpVerification kcdOptVerficationDto: {}", kcdOtpVerficationDto);

        String result = this.kcdOtpAuthService.verifyOtp(kcdOtpVerficationDto);

        // 에러가 없다면 spring security ROLE_USER 권한 부여
        roleService.addRoleUser(request);

        return new KcdAuthResponse<>(result);
    }

    @Operation(summary = "Opt 검증 후 발급된 Role 삭제 Api", description = "Opt 검증 후 발급된 Role 삭제 Api")
    @GetMapping("/delete/role")
    public KcdAuthResponse<Boolean> deleteRole(
            @Parameter(description = "삭제할 RoleName ex: ROLE_USER", name = "roleName")
            @RequestParam(name="roleName", required = true, defaultValue = "ROLE_USER") String roleName ,
            HttpServletRequest request) {
        this.roleService.deleteRole(roleName, request);
        return new KcdAuthResponse<>(Boolean.TRUE);
    }

    private void parameterValidation(KcdOptDto kcdOptDto) {
        if (StringUtils.isBlank(kcdOptDto.getMobile()) && StringUtils.isBlank(kcdOptDto.getEmail()))
            throw new AuthException("핸드폰 번호나 이메일 중 하나는 반드시 입력해야 합니다.");
    }

}