package com.example.authservice.core.authentication;

import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.ports.out.EventPublisherPort;
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
