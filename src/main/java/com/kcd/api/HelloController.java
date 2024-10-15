package com.kcd.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Tag(name = "Health Check Api", description = "Health Check Controller")
public class HelloController {

    @Value("${server.port}")
    private int serverPort;

    @Value("${CLIENT_ID}")
    private String clientId;

    @Value("${CLIENT_SECRET}")
    private String clientSecret;

    @GetMapping(value = "/hello")
    @Operation(summary = "Health Check Api", description = "Health Check Api")
    public String hello() {
        log.info("Hello World");
        return "hello serverPort: " + this.serverPort + ", clientId: " + this.clientId + ", clientSecret: " + this.clientSecret;
    }
}
