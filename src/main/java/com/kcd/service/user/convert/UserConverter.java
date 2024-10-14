package com.kcd.service.user.convert;
import com.kcd.repository.user.entity.UserEntity;
import com.kcd.service.user.dto.UserDto;
import java.time.LocalDateTime;

public class UserConverter {
    public static UserDto toDomain(UserEntity userEntity) {
        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .mobile(userEntity.getMobile())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .birthday(userEntity.getBirthday())
                .rrn7th(userEntity.getRrn7th())
                .gender(userEntity.getGender())
                .nationality(userEntity.getNationality())
                .tsp(userEntity.getTsp())
                .provider(userEntity.getProvider())
                .createdAt(userEntity.getCreatedAt())
                .build();
    }

    public static UserEntity toEntity(UserDto userDto) {
        return UserEntity.builder()
                .name(userDto.getName())
                .mobile(userDto.getMobile())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .birthday(userDto.getBirthday())
                .rrn7th(userDto.getRrn7th())
                .gender(userDto.getGender())
                .nationality(userDto.getNationality())
                .tsp(userDto.getTsp())
                .provider(userDto.getProvider())
                .createdAt(LocalDateTime.now())
                .build();
    }
}
