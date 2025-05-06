package com.example.authservice.infrastructure.integration.userservice;

import com.example.authservice.domain.dto.auth.RegisterUserDto;
import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.ports.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserServiceAdapter implements UserServicePort {
    private final UserServiceFeignClient client;


    @Override
    public UserId register(RegisterUserDto userData) {
        ResponseEntity<Long> response = client.register(userData);
        if (response.getStatusCode().is2xxSuccessful()) {
            return new UserId(response.getBody());
        }
        return null;
    }
}
