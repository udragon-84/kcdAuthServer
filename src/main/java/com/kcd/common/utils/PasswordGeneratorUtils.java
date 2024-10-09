package com.kcd.common.utils;

import lombok.experimental.UtilityClass;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@UtilityClass
public class PasswordGeneratorUtils {

    public static String generatePassword(String word) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(word);
    }

    public static void main(String[] args) {
        System.out.println(generatePassword("password"));
    }
}
