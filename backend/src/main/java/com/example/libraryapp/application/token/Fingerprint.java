package com.example.libraryapp.application.token;

import jakarta.servlet.http.Cookie;

record Fingerprint(
        String text,
        String hash,
        Cookie cookie
) { }

