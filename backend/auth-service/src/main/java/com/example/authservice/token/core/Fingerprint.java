package com.example.authservice.token.core;

import jakarta.servlet.http.Cookie;

record Fingerprint(
        String text,
        String hash,
        Cookie cookie
) { }

