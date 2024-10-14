package com.kcd.service.otp;
import com.kcd.service.otp.dto.KcdOptDto;
import com.kcd.service.otp.dto.KcdOtpVerficationDto;

import java.util.Map;

public interface KcdOtpAuthService {
    Map<String, String> issueOtp(KcdOptDto kcdOptDto);
    String verifyOtp(KcdOtpVerficationDto kcdOtpVerficationDto);
}
