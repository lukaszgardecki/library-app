package com.example.authservice.domain.dto.token;

import com.example.authservice.domain.model.token.values.TokenType;

public record TokenInfoDto(String hash, TokenType type) {
}
