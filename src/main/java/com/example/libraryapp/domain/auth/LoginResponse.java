package com.example.libraryapp.domain.auth;

import lombok.*;

@Getter
@Setter
public class LoginResponse {
    private String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
