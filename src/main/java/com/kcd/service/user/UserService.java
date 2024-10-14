package com.kcd.service.user;

import com.kcd.service.user.dto.UserDto;

public interface UserService {
    UserDto findByEmailOrMobile(String encryptEmail, String encryptMobile);
    Boolean registerUser(UserDto userDto);
    UserDto getKcdUserProfile(String sessionToken);
}
