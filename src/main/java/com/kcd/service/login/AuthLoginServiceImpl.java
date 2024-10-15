package com.kcd.service.login;

import com.kcd.common.exception.AuthException;
import com.kcd.service.login.dto.AuthLoginDto;
import com.kcd.service.user.UserService;
import com.kcd.service.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
public class AuthLoginServiceImpl implements AuthLoginService {

    @Autowired
    private UserService userService;

    @Override
    @Transactional(readOnly = true)
    public UserDto authLogin(AuthLoginDto authLoginDto) {
        // 이메일 여부 확인
        boolean isEmail = authLoginDto.getLoginId().contains("@");
        // 필요한 필드 암호화
        authLoginDto.encryptFields();
        // 이메일 또는 휴대전화번호로 검색
        UserDto userDto = isEmail ?
                this.userService.findByEmail(authLoginDto.getLoginId()) :
                this.userService.findByMobile(authLoginDto.getLoginId());

        if (Objects.isNull(userDto)) {
            throw new AuthException(String.format("로그인 Id %s 정보를 찾을 수 없습니다.", authLoginDto.getLoginId()));
        }

        if (!authLoginDto.getPassword().equals(userDto.getPassword())) {
            throw new AuthException("비밀번호가 일치하지 않습니다.");
        }

        return userDto;
    }
}
