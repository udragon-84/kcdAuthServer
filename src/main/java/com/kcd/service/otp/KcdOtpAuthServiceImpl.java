package com.kcd.service.otp;

import com.kcd.repository.otp.KcdOtpRepository;
import com.kcd.service.otp.dto.KcdOptAuthDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KcdOtpAuthServiceImpl implements KcdOtpAuthService {

    @Autowired
    private KcdOtpRepository kcdOtpRepository;

    @Override
    public String issueOTP(KcdOptAuthDto kcdOptAuthDto) {

        return "";
    }
}
