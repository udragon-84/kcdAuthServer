package com.kcd.service.user.dto;

import com.kcd.common.jwt.JwtClaims;
import java.util.Map;
import static java.util.Map.entry;

public class UserJwtClaimsGenerator implements JwtClaims {

    private final UserDto userDto;

    public UserJwtClaimsGenerator(UserDto userDto) {
        this.userDto = userDto;
    }

    @Override
    public Map<String, Object> generateClaimsMap() {
        return Map.ofEntries(
                entry("id", this.userDto.getId()),
                entry("name", this.userDto.getName()),
                entry("mobile", this.userDto.getMobile()),
                entry("email", this.userDto.getEmail()),
                entry("birthday", this.userDto.getBirthday()),
                entry("rrn7th", this.userDto.getRrn7th()),
                entry("gender", this.userDto.getGender()),
                entry("nationality", this.userDto.getNationality()),
                entry("tsp", this.userDto.getTsp()),
                entry("provider", this.userDto.getProvider()));
    }
}
