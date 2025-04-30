package com.example.authservice.core.authentication;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ExtractTokenFromHeaderUseCase {
    private final HttpRequestExtractor extractor;

    String execute(String authHeader) {
        return extractor.extractTokenFromHeader(authHeader);
    }
}
