package com.example.authservice.token.domain.dto;

import com.example.authservice.token.domain.model.TokenType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {
    private Long id;
    private String token;
    private TokenType tokenType;
    private boolean expired;
    private boolean revoked;
    private Long userId;

    public TokenDto(Long userId, String token) {
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


