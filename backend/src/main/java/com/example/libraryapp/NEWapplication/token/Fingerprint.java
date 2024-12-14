package com.example.libraryapp.NEWapplication.token;

import jakarta.servlet.http.Cookie;

record Fingerprint(
        String text,
        String hash,
        Cookie cookie
) { }

