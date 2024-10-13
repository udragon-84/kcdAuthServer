package com.kcd.common.encrypt;

import com.kcd.common.exception.DecryptionFailedException;
import com.kcd.common.exception.EncryptionFailedException;
import lombok.extern.slf4j.Slf4j;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES-128 암호화 추상 클래스
 */
@Slf4j
public abstract class Aes128Encryptor {
    private static final String ALGORITHM;
    private static final String SECRET_KEY;

    static {
        ALGORITHM = "AES"; // System.getenv("AES_KEY");  // AES
        SECRET_KEY = "0123456789abcdef"; // System.getenv("SECRET_KEY");  // 16바이트 키 값 (AES-128용)

        if (ALGORITHM == null || SECRET_KEY == null) {
            throw new IllegalArgumentException("Aes128Encryptor init static 환경 변수 AES_KEY 또는 SECRET_KEY가 설정되지 않았습니다.");
        }
    }

    // 암호화 메서드 (AES-128 결정적 암호화)
    protected String encrypt(String data) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  // ECB 모드 사용
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptedData = cipher.doFinal(data.getBytes());
            return Base64.getEncoder().encodeToString(encryptedData);
        } catch (Exception e) {
            throw new EncryptionFailedException("암호화 오류", e);
        }
    }

    // 복호화 메서드
    protected String decrypt(String encryptedData) {
        try {
            SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");  // ECB 모드 사용
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
            return new String(cipher.doFinal(decodedBytes));
        } catch (Exception e) {
            throw new DecryptionFailedException("복호화 오류", e);
        }
    }

    protected String processField(String value, boolean encrypt) {
        return (value == null || value.isEmpty()) ? value : (encrypt ? encrypt(value) : decrypt(value));
    }

    // 상속받는 클래스에서 암호화/복호화를 적용할 추상 메서드
    public abstract void encryptFields();
    public abstract void decryptFields();
}
