package com.kcd.service.user;

import com.kcd.service.user.dto.UserDto;
import jakarta.validation.constraints.NotBlank;

public interface UserService {
    UserDto findByEmailOrMobile(String encryptEmail, String encryptMobile);
    Boolean registerUser(UserDto userDto);
    UserDto getKcdUserProfile(String sessionToken);
    UserDto findByEmail(String encryptEmail);
    UserDto findByMobile(String encryptMobile);
}
