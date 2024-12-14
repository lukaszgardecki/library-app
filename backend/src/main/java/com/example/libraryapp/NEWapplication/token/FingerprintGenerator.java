package com.example.libraryapp.NEWapplication.token;

import jakarta.servlet.http.Cookie;

import java.security.SecureRandom;

class FingerprintGenerator {
    static final String FINGERPRINT_NAME = "userFingerprint";
    static final String FINGERPRINT_COOKIE_NAME = "myCookie";
    //    public static final String FINGERPRINT_COOKIE_NAME = "__Secure-Fgp";
    private static final int FINGERPRINT_LENGTH = 50;
    private static final String FINGERPRINT_COOKIE_HEADER = "Set-Cookie";
    private static final String FINGERPRINT_COOKIE_PATTERN = FINGERPRINT_COOKIE_NAME + "=%s; SameSite=Strict; HttpOnly"; // add Secure if the SSL certificate is available

    static Fingerprint generate() {
        String text = generateStringOfLength(FINGERPRINT_LENGTH);
        return new Fingerprint(
                text,
            TokenUtils.hashWithSHA256(text),
            generateFingerprintCookie(text)
        );
    }

    private static String generateStringOfLength(int length) {
        SecureRandom secureRandom = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&*+-/?@^_";
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return randomString.toString();
    }

    private static Cookie generateFingerprintCookie(String text) {
        return new Cookie(FINGERPRINT_COOKIE_HEADER, FINGERPRINT_COOKIE_PATTERN.formatted(text));
    }

    public static boolean isFgpCookieSecured() {
        return FINGERPRINT_COOKIE_NAME.equals("__Secure-Fgp");
    }
}
