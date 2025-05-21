package com.example.authservice.domain.model.token;

public record Auth(
        Token accessToken,
        Token refreshToken,
        CookieValues cookieValues
) { }
