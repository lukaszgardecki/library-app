package com.example.authservice.domain.dto.token;

import jakarta.servlet.http.Cookie;

public record TokenAuth(
        String accessToken,
        String refreshToken,
        Cookie cookie
) { }
