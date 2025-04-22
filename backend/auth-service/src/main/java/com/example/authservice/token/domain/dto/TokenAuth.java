package com.example.authservice.token.domain.dto;

import jakarta.servlet.http.Cookie;

public record TokenAuth(
        String accessToken,
        String refreshToken,
        Cookie cookie
) { }
