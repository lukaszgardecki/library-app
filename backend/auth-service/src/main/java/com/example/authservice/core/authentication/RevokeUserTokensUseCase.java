package com.example.authservice.core.authentication;

import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class RevokeUserTokensUseCase {
    private final TokenService tokenService;
    private final EventPublisherPort publisher;

    void execute(UserId userId) {
        tokenService.revokeUserTokens(userId);
        publisher.publishLogoutSuccessEvent(userId);
    }
}
