package com.kcd.service.otp;

import com.kcd.service.otp.dto.KcdOtpAuthDto;
import com.kcd.service.otp.dto.KcdOtpVerficationDto;

import java.util.Map;

public interface KcdOtpAuthService {
    Map<String, String> issueOtp(KcdOtpAuthDto kcdOtpAuthDto);
    String verifyOtp(KcdOtpVerficationDto kcdOtpVerficationDto);
}
