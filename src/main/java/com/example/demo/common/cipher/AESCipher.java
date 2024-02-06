package com.example.demo.common.cipher;

import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

@UtilityClass
public class AESCipher {
    private static final String ALGORITHM = "AES/ECB/PKCS5Padding";
    private static final byte[] KEY = "abcdefghijklmnopqr".substring(0, 16).getBytes(StandardCharsets.UTF_8);
    private static final Key key = new SecretKeySpec(KEY, "AES");

    public static String encode(String value) {
        if (value == null) {
            return null;
        }

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return new String(Base64.getEncoder().encode(cipher.doFinal(value.getBytes())));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String decode(String value) {
        if (value == null) {
            return null;
        }
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(cipher.doFinal(Base64.getDecoder().decode(value)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
