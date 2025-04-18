package com.example.libraryapp.application.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ExtractTokenFromHeaderUseCase {
    private final HttpRequestExtractor extractor;

    String execute(HttpServletRequest request) {
        return extractor.extractTokenFromHeader(request);
    }
}
