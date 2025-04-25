package com.example.requestservice.infrastructure.integration.userservice;

import com.example.requestservice.domain.model.UserId;
import com.example.requestservice.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserServiceAdapter implements UserServicePort {
    private final UserServiceFeignClient client;


    @Override
    public void verifyUserForBookItemRequest(UserId userId) {
        ResponseEntity<Void> response = client.verifyUserForBookItemRequest(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {

        }
        // TODO: 24.04.2025 jeśli nie to?
    }
}
