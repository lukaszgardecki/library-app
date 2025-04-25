package com.example.loanservice.infrastructure.integration.userservice;

import com.example.loanservice.domain.model.UserId;
import com.example.loanservice.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserServiceAdapter implements UserServicePort {
    private final UserServiceFeignClient client;

    @Override
    public void verifyUserForBookItemLoan(UserId userId) {
        ResponseEntity<Void> response = client.verifyUserForBookItemLoan(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {

        }
        // TODO: 24.04.2025 jak błąd to co wtedy?
    }

    @Override
    public void verifyUserForBookItemRenewal(UserId userId) {
        ResponseEntity<Void> response = client.verifyUserForBookItemRenewal(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {

        }
        // TODO: 24.04.2025 jak błąd to co wtedy?
    }
}
