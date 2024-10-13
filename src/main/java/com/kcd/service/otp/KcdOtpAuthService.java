package com.kcd.service.otp;

import com.kcd.service.otp.dto.KcdOtpVerficationDto;
import com.kcd.service.user.dto.UserDto;

import java.util.Map;

public interface KcdOtpAuthService {
    Map<String, String> issueOtp(UserDto userDto);
    String verifyOtp(KcdOtpVerficationDto kcdOtpVerficationDto);
}
