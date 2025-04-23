package com.example.authservice.domain.model.token;

import lombok.Getter;

@Getter
public enum TokenType {
    BEARER("Bearer ");

    private final String prefix;

    TokenType(String prefix) {
        this.prefix = prefix;
    }
}


