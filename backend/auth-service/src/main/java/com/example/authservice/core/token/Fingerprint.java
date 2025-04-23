package com.example.authservice.core.token;

import jakarta.servlet.http.Cookie;

record Fingerprint(
        String text,
        String hash,
        Cookie cookie
) { }

