package com.kcd.api;

import com.kcd.api.response.KcdAuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user")
@Tag(name = "kcd 회원 Api 명세", description = "kcd 회원 Controller")
public class KcdUserController {

    @PreAuthorize("hasRole('USER')")
    //@PostMapping("/register")
    @GetMapping("/register")
    public KcdAuthResponse<Boolean> kcdUserRegister() {
        log.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return new KcdAuthResponse<>(Boolean.TRUE);
    }


}
