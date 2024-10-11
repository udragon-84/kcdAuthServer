package com.kcd.config;

import com.kcd.service.oauth2.CustomOAuth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;


import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class KcdAuthServerSecurityConfig {

    @Autowired
    private CustomOAuth2UserService oauth2UserComponent;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/actuator/**").authenticated() // actuator 엔드포인트 인증 필요 admin/password
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/login", "/oauth2/**", "/signup").permitAll()
                        .requestMatchers("/register").hasRole("USER") //회원 가입 같은 경우는 ROLE_USER를 획득한 경우에만 접근 가능
                        .anyRequest()
                        .permitAll() // 그 외 모든 요청 허용
                )
                .httpBasic(withDefaults()) // 기본 HTTP 인증 사용
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)  // 같은 출처에서 프레임 허용 /h2-console 사용시 필요
                )
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oauth2UserComponent))
                        .defaultSuccessUrl("/signup",true));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionSecurityContextRepository httpSessionSecurityContextRepository() {
        return new HttpSessionSecurityContextRepository();
    }

}
