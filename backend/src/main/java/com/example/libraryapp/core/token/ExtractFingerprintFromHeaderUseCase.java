package com.example.libraryapp.core.token;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ExtractFingerprintFromHeaderUseCase {
    private final HttpRequestExtractor extractor;

    String execute(HttpServletRequest request) {
        return extractor.extractFingerprintFromHeader(request);
    }
}
