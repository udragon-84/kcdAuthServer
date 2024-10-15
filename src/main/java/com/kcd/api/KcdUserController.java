package com.kcd.api;

import com.kcd.api.response.KcdAuthResponse;
import com.kcd.common.exception.AuthenticationException;
import com.kcd.service.user.UserService;
import com.kcd.service.user.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "kcd 회원 Api 명세", description = "kcd 회원 Controller")
public class KcdUserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('USER')")
    @PostMapping("/register")
    @Operation(summary = "Kcd 회원가입 Api", description = "Kcd 회원가입 Api")
    public KcdAuthResponse<Boolean> kcdUserRegister(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "KCD 회원 가입 json 파라메터 정의",
                    required = true,
                    content = @Content(schema = @Schema(implementation = UserDto.class), examples = @ExampleObject(
                            value = "{ \"name\": \"유창근\", " +
                                    "\"mobile\": \"010-7318-5308\", " +
                                    "\"email\": \"youzang7@naver.com\", " +
                                    "\"password\": \"**********\", " +
                                    "\"birthday\": \"841021\", " +
                                    "\"rrn7th\": \"1015724\", " +
                                    "\"gender\": \"남\", " +
                                    "\"nationality\": \"대한민국\", " +
                                    "\"tsp\": \"SK\", " +
                                    "\"provider\": \"KAKAO\" }"
                    )))
            @Validated @RequestBody UserDto userDto
    ) {
        log.info("KcdUserController.kcdUserRegister: {}", userDto);
        return new KcdAuthResponse<>(this.userService.registerUser(userDto));
    }

    @GetMapping("/profile")
    @Operation(summary = "로그인 후 자신의 정보 조회 Api", description = "로그인 후 자신의 정보 조회 Api")
    public KcdAuthResponse<UserDto> getKcdUserProfile(HttpServletRequest request) {
        String sessionToken = Optional.ofNullable(request.getSession().getAttribute("ID"))
                .map(Object::toString)
                .orElseThrow(() -> new AuthenticationException("자신의 정보는 로그인 후 조회 가능합니다."));
        return new KcdAuthResponse<>(this.userService.getKcdUserProfile(sessionToken));
    }
}
