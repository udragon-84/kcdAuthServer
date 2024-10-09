package com.kcd.service.otp;

import com.kcd.service.otp.dto.KcdOptAuthDto;
import jakarta.validation.Valid;

public interface KcdOtpAuthService {
    String issueOTP(@Valid KcdOptAuthDto kcdOptAuthDto);
}
