package com.example.userservice.infrastructure.integration.fineservice;

import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.ports.FineServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class FineServiceAdapter implements FineServicePort {
    private final FineServiceFeignClient client;

    @Override
    public void validateUserForFines(UserId userId) {
        client.validateUserForFines(userId.value());
    }
}
