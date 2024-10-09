package com.kcd.service.otp;

import com.kcd.common.exception.AuthException;
import com.kcd.repository.otp.KcdOtpRepository;
import com.kcd.service.enums.RedisTTLEnum;
import com.kcd.service.otp.dto.KcdOtpAuthDto;
import com.kcd.service.otp.dto.KcdOtpVerficationDto;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class KcdOtpAuthServiceImpl implements KcdOtpAuthService {

    @Autowired
    private KcdOtpRepository kcdOtpRepository;
    private final SecureRandom random = new SecureRandom();

    @Override
    public Map<String, String> issueOtp(KcdOtpAuthDto kcdOtpAuthDto) {
        Map<String, String> otpMap = this.generatedOtpMap(kcdOtpAuthDto);
        this.saveRedisOtp(otpMap);
        return otpMap;
    }

    @Override
    public String verifyOtp(KcdOtpVerficationDto kcdOtpVerficationDto) {
        String userOtp = this.kcdOtpRepository.getOtp(kcdOtpVerficationDto.getId());

        if (StringUtils.isBlank(userOtp))
            throw new AuthException(String.format("id [%s] otp 인증시간이 만료되었거나 otp 값이 존재하지 않습니다.", kcdOtpVerficationDto.getId()));

        if (!Objects.equals(userOtp, kcdOtpVerficationDto.getOtp()))
            throw new AuthException(String.format("id [%s] 발급된 opt 값이 일치하지 않습니다.", kcdOtpVerficationDto.getId()));

        if (!this.deleteRedisOtp(kcdOtpVerficationDto.getId()))
            throw new AuthException(String.format("id [%s] 발급된 opt 삭제시 에러가 발생되었습니다.", kcdOtpVerficationDto.getId()));

        return kcdOtpVerficationDto.getId();
    }

    private boolean deleteRedisOtp(String id) {
        return this.kcdOtpRepository.deleteOtp(id);
    }

    private Map<String, String> generatedOtpMap(KcdOtpAuthDto kcdOtpAuthDto) {
        Map<String, String> otpMap = new HashMap<>();
        String otpKey = StringUtils.isBlank(kcdOtpAuthDto.getEmail()) ? kcdOtpAuthDto.getMobile() : kcdOtpAuthDto.getEmail();
        String otpValue = this.generateOTP();
        otpMap.put("id", otpKey);
        otpMap.put("otp", otpValue);
        return otpMap;
    }

    private void saveRedisOtp(Map<String, String> otpMap) {
        this.kcdOtpRepository.saveOtp(otpMap.get("id"), otpMap.get("otp"),
                RedisTTLEnum.OTP_VERIFICATION.getDuration(),
                RedisTTLEnum.OTP_VERIFICATION.getTimeUnit());
    }

    private String generateOTP() {
        int otp = random.nextInt(900000) + 100000; // 6자리 OTP 생성
        return String.valueOf(otp);
    }

}
