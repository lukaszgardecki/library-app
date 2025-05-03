package com.example.authservice.domain.model.token;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Token {
    private Long id;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private Long userId;

    public Token(Long userId, String token, TokenType type) {
        this.userId = userId;
        this.token = token;
        this.tokenType = type;
        this.expired = false;
        this.revoked = false;
    }

    public void setInvalid() {
        this.expired = true;
        this.revoked = true;
    }
}


