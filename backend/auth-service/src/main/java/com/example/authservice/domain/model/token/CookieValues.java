package com.example.authservice.domain.model.token;

public record CookieValues(
        String text,
        String hash,
        String cookieName
) { }

