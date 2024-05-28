package com.example.libraryapp;

import jakarta.servlet.http.Cookie;
import lombok.Getter;

@Getter
public class TestAuth {
    String accessToken;
    String refreshToken;
    Cookie fgpCookie;

    public TestAuth(String accessToken, String refreshToken, Cookie fgpCookie) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.fgpCookie = fgpCookie;
    }
}
