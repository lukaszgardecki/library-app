package com.example.authservice.core.authentication;

import com.example.authservice.domain.Constants;
import com.example.authservice.domain.model.token.CookieValues;

import java.security.SecureRandom;

class CookieValueGenerator {
    static final String FINGERPRINT_NAME = "userFingerprint";
    //    public static final String FINGERPRINT_COOKIE_NAME = "__Secure-Fgp";
    private static final int FINGERPRINT_LENGTH = 50;

    static CookieValues generate() {
        String text = generateString();
        return new CookieValues(text, TokenUtils.hashWithSHA256(text), Constants.AUTH_COOKIE_NAME);
    }

    private static String generateString() {
        SecureRandom secureRandom = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
        StringBuilder randomString = new StringBuilder(FINGERPRINT_LENGTH);
        for (int i = 0; i < FINGERPRINT_LENGTH; i++) {
            randomString.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return randomString.toString();
    }
}
