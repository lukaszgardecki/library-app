package com.example.userservice.infrastructure.integration.authservice;

import com.example.userservice.domain.model.user.values.UserId;
import com.example.userservice.infrastructure.integration.authservice.dto.AuthDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthServiceAdapter {
    private final AuthServiceFeignClient client;


    public AuthDetailsDto getUserAuthByUserId(UserId userId) {
        ResponseEntity<AuthDetailsDto> response = client.getAuthDetailsByUserId(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
