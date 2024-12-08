package com.harender.user_auth_system.utils;

import java.security.SecureRandom;
import java.util.Base64;

public class KeyGenerator {

    // Static helper function to generate a secure 512-bit Base64 key
    public static String generateSecureKey() {
        byte[] key = new byte[64]; // 64 bytes = 512 bits
        new SecureRandom().nextBytes(key);  // Generate random bytes
        return Base64.getEncoder().encodeToString(key);  // Base64 encode the key
    }

    public static void main(String[] args) {
        // For testing the key generation
        String base64Key = generateSecureKey();
        System.out.println("Generated Base64 Key: " + base64Key);
    }
}
