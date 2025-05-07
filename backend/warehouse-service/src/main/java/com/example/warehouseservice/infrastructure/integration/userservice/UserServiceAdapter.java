package com.example.warehouseservice.infrastructure.integration.userservice;

import com.example.warehouseservice.domain.integration.user.dto.PersonDto;
import com.example.warehouseservice.domain.integration.user.dto.UserDto;
import com.example.warehouseservice.domain.integration.user.UserId;
import com.example.warehouseservice.domain.ports.out.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class UserServiceAdapter implements UserServicePort {
    private final UserServiceFeignClient client;

    @Override
    public UserDto getUserById(UserId userId) {
        ResponseEntity<UserDto> response = client.getUserById(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public PersonDto getPersonByUserId(UserId userId) {
        ResponseEntity<PersonDto> response = client.getPersonByUserId(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
