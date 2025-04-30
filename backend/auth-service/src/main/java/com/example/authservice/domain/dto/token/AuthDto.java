package com.example.authservice.domain.dto.token;

public record AuthDto(
        String accessToken,
        String refreshToken,
        String cookieName,
        String cookieValue
) { }
