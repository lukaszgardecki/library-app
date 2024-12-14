package com.example.libraryapp.NEWdomain.token.model;

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

    public Token(Long userId, String token) {
        this.userId = userId;
        this.token = token;
        this.tokenType = TokenType.BEARER;
        this.expired = false;
        this.revoked = false;
    }

    public void setInvalid() {
        this.expired = true;
        this.revoked = true;
    }
}


