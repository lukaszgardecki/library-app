package com.example.authservice.infrastructure.http.authentication.dto;

public record AuthDto(
        String accessToken,
        String refreshToken,
        String cookieName,
        String cookieValue
) { }
