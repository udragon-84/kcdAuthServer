package com.kcd.service.user;

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
}
