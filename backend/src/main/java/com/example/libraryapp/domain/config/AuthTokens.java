package com.example.libraryapp.domain.config;

public record AuthTokens (
        String accessToken,
        String refreshToken,
        Fingerprint fingerprint
) { }
