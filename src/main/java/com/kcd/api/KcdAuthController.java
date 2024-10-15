package com.kcd.api;

import com.kcd.api.response.KcdAuthResponse;
import com.kcd.common.session.SessionCookieManager;
import com.kcd.service.login.AuthLoginService;
import com.kcd.service.login.dto.AuthLoginDto;
import com.kcd.service.user.dto.UserJwtClaimsGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@Tag(name = "kcd 회원 로그인 Api 명세", description = "kcd 회원 로그인 Controller")
public class KcdAuthController {

    private final AuthLoginService authLoginService;
    private final SessionCookieManager sessionCookieManager;

    @Autowired
    public KcdAuthController(AuthLoginService authLoginService, SessionCookieManager sessionCookieManager) {
        this.authLoginService = authLoginService;
        this.sessionCookieManager = sessionCookieManager;
    }

    @PostMapping("/login")
    @Operation(summary = "Kcd 회원 로그인 Api", description = "Kcd 회원 로그인 Api")
    public KcdAuthResponse<Boolean> kcdUserLogin(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "KCD 회원 로그인 json 파라메터 정의",
                    required = true,
                    content = @Content(schema = @Schema(implementation = AuthLoginDto.class), examples = @ExampleObject(
                            value = "{ \"loginId\": \"youzang7@naver.com\", " +
                                    "\"password\": \"\" " + "}"
                    )))
            @Validated @RequestBody AuthLoginDto authLoginDto,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        log.info("KcdUserController.kcdUserLogin: {}", authLoginDto);
        this.sessionCookieManager.processSessionAndCookie(
                new UserJwtClaimsGenerator(this.authLoginService.authLogin(authLoginDto)),
                request,
                response
        );
        return new KcdAuthResponse<>(true);
    }

    @PostMapping("/logout")
    @Operation(summary = "Kcd 회원 로그아웃 Api", description = "Kcd 회원 로그아웃 Api")
    public KcdAuthResponse<Boolean> kcdUserLogOut( HttpServletRequest request, HttpServletResponse response) {
        log.info("KcdUserController.kcdUserLogOut logout");
        this.sessionCookieManager.invalidateSessionAndCookie(request, response);
        return new KcdAuthResponse<>(true);
    }

}
