package com.example.libraryapp.domain.config;

import jakarta.servlet.http.Cookie;
import lombok.Getter;

@Getter
public class Fingerprint {
    private final String text;
    private final String hash;
    private final Cookie cookie;

    public Fingerprint() {
        this.text = SecurityUtils.generateFingerprintValue();
        this.hash = SecurityUtils.hashWithSHA256(text);
        this.cookie = SecurityUtils.generateFingerprintCookie(text);
    }
}
