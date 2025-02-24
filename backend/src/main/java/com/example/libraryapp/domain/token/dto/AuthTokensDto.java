package com.example.libraryapp.domain.token.dto;

import jakarta.servlet.http.Cookie;

public record AuthTokensDto(
        String accessToken,
        String refreshToken,
        Cookie cookie
) { }
