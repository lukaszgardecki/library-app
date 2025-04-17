package com.example.libraryapp.core.token;

import jakarta.servlet.http.Cookie;

record Fingerprint(
        String text,
        String hash,
        Cookie cookie
) { }

