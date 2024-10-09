package com.kcd.api;

import com.kcd.api.response.KcdAuthResponse;
import com.kcd.service.otp.KcdOtpAuthService;
import com.kcd.service.otp.dto.KcdOptAuthDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/otp")
@Tag(name = "kcd OTP 인증 Controller", description = "kcd OTP 인증 Api 명세")
public class KcdOtpAuthController {

    @Autowired
    private KcdOtpAuthService kcdOtpAuthService;

    @Operation(summary = "Opt 발급 Api", description = "Opt 발급 Api")
    @PostMapping("/issue")
    public KcdAuthResponse<String> issue(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Opt 발급 json 파라메터 정의",
                    required = true,
                    content = @Content(schema = @Schema(implementation = KcdOptAuthDto.class), examples = @ExampleObject(
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
            @Validated @RequestBody KcdOptAuthDto kcdOptAuthDto) {
        log.info("KcdOtpAuthController.issue kcdOptAuthDto: {}", kcdOptAuthDto);
        String otp = this.kcdOtpAuthService.issueOTP(kcdOptAuthDto);
        return new KcdAuthResponse<>(otp);
    }

}