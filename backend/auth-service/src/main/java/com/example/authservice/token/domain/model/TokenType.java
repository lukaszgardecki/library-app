package com.example.authservice.token.domain.model;

import lombok.Getter;

@Getter
public enum TokenType {
    BEARER("Bearer ");

    private final String prefix;

    TokenType(String prefix) {
        this.prefix = prefix;
    }
}


