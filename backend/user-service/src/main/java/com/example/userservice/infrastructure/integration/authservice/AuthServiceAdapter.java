package com.example.userservice.infrastructure.integration.authservice;

import com.example.userservice.domain.dto.user.UserAuthDto;
import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.ports.AuthenticationServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class AuthServiceAdapter implements AuthenticationServicePort {
    private final AuthServiceFeignClient client;


    @Override
    public UserAuthDto getUserAuthByUserId(UserId userId) {
        ResponseEntity<UserAuthDto> response = client.getUserAuthByUserId(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
