package com.example.libraryapp.domain.token.model;

import lombok.Getter;

@Getter
public enum TokenType {
    BEARER("Bearer ");

    private final String prefix;

    TokenType(String prefix) {
        this.prefix = prefix;
    }
}


