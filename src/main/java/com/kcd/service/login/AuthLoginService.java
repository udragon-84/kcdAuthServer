package com.kcd.service.login;

import com.kcd.service.login.dto.AuthLoginDto;
import com.kcd.service.user.dto.UserDto;

public interface AuthLoginService {
    UserDto authLogin(AuthLoginDto authLoginDto);
}