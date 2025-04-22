package com.example.userservice.user.infrastructure.integration.authservice;

import com.example.userservice.user.domain.dto.UserAuthDto;
import com.example.userservice.user.domain.model.user.UserId;
import com.example.userservice.user.domain.ports.AuthenticationServicePort;
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
