package com.kcd.service.user;

import com.kcd.common.exception.UserAlreadyExistsException;
import com.kcd.common.jwt.JwtTokenProvider;
import com.kcd.repository.user.UsersRepository;
import com.kcd.service.user.convert.UserConverter;
import com.kcd.service.user.dto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Override
    @Transactional(readOnly = true)
    public UserDto findByEmailOrMobile(String encryptEmail, String encryptMobile) {
        return this.usersRepository.findByEmailOrMobile(encryptEmail, encryptMobile)
                .map(UserConverter::toDomain)
                .stream()
                .peek(UserDto::decryptFields)
                .findAny()
                .orElse(null);
    }

    @Override
    @Transactional
    public Boolean registerUser(UserDto userDto) {
        userDto.encryptFields(); // userDto 암호화 처리
        if (this.usersRepository.existsByEmailOrMobile(userDto.getEmail(), userDto.getMobile())) {
            userDto.decryptFields();
            throw new UserAlreadyExistsException(
                    String.format(
                            "이미 해당 이메일(%s) 또는 전화번호(%s)로 가입된 사용자가 존재합니다. 중복된 정보로 회원가입이 불가능합니다.",
                            userDto.getEmail(),
                            userDto.getMobile()
                    )
            );
        }
        this.usersRepository.save(UserConverter.toEntity(userDto));
        return true;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto getKcdUserProfile(String sessionToken) {
        String userId = jwtTokenProvider.getSubject(sessionToken);
        log.info("UserServiceImpl.getKcdUserProfile userId: {}", userId);
        return this.usersRepository.findById(Long.parseLong(userId))
                .map(UserConverter::toDomain)
                .stream()
                .peek(UserDto::decryptFields)
                .findAny()
                .orElse(null);
    }

}
