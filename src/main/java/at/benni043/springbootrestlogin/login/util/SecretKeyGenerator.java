package at.benni043.springbootrestlogin.login.util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static SecretKey generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] keyBytes = new byte[32]; // 256 bits
        secureRandom.nextBytes(keyBytes);
        return new SecretKeySpec(keyBytes, "HmacSHA256");
    }

    public static void main(String[] args) {
        SecretKey secretKey = generateSecretKey();
        System.out.println("Secret Key: " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));
    }

}
