package com.example.authservice.domain.model.token;

import com.example.authservice.domain.model.token.values.TokenType;

public record TokenInfo(String hash, TokenType type) {
}
