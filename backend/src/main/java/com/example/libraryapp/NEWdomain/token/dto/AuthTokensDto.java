package com.example.libraryapp.NEWdomain.token.dto;

import jakarta.servlet.http.Cookie;

public record AuthTokensDto(
        String accessToken,
        String refreshToken,
        Cookie cookie
) { }
