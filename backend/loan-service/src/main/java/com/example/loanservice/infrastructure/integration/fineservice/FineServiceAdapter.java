package com.example.loanservice.infrastructure.integration.fineservice;

import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.FineServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class FineServiceAdapter implements FineServicePort {
    private final FineServiceFeignClient client;

    @Override
    public void verifyUserForFines(UserId userId) {
        ResponseEntity<Void> response = client.verifyUserForFines(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {

        }
        // TODO: 24.04.2025 ???
    }
}
