package com.example.libraryapp.domain.config;

import jakarta.servlet.http.Cookie;
import jakarta.xml.bind.DatatypeConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class SecurityUtils {
    public static final String ID_CLAIM_NAME = "userId";
    public static final String USER_ROLE = "userRole";
    public static final String FINGERPRINT_NAME = "userFingerprint";
    public static final String FINGERPRINT_COOKIE_NAME = "myCookie";
    //    public static final String FINGERPRINT_COOKIE_NAME = "__Secure-Fgp";
    public static final String FINGERPRINT_COOKIE_HEADER = "Set-Cookie";
    private static final String FINGERPRINT_COOKIE_PATTERN = FINGERPRINT_COOKIE_NAME + "=%s; SameSite=Strict; HttpOnly"; // add Secure if the SSL certificate is available
    private static final int FINGERPRINT_LENGTH = 50;

    public static String generateFingerprintValue() {
        return generateStringOfLength(FINGERPRINT_LENGTH);
    }

    public static Cookie generateFingerprintCookie(String text) {
        return new Cookie(FINGERPRINT_COOKIE_HEADER, FINGERPRINT_COOKIE_PATTERN.formatted(text));
    }

    public static String generateStringOfLength(int length) {
        SecureRandom secureRandom = new SecureRandom();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!#$%&*+-/?@^_";
        StringBuilder randomString = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            randomString.append(characters.charAt(secureRandom.nextInt(characters.length())));
        }
        return randomString.toString();
    }

    public static String hashWithSHA256(String userFingerprint) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] userFingerprintDigest = digest.digest(userFingerprint.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(userFingerprintDigest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not found", e);
        }
    }

    public static String bytesToHex(byte[] bytes) {
        return DatatypeConverter.printHexBinary(bytes);
    }

    public static boolean isFgpCookieSecured() {
        return FINGERPRINT_COOKIE_NAME.equals("__Secure-Fgp");
    }
}
