package com.example.libraryapp.core.token;

import com.example.libraryapp.domain.token.dto.TokenDto;
import com.example.libraryapp.domain.token.model.Token;

class TokenMapper {

    static Token toEntity(TokenDto dto) {
        return new Token(
                dto.getId(),
                dto.getToken(),
                dto.getTokenType(),
                dto.isExpired(),
                dto.isRevoked(),
                dto.getUserId()
        );
    }

    static TokenDto toDto(Token model) {
        return new TokenDto(
                model.getId(),
                model.getToken(),
                model.getTokenType(),
                model.isExpired(),
                model.isRevoked(),
                model.getUserId()
        );
    }
}
