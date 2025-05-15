package com.example.requestservice.infrastructure.integration.userservice;

import com.example.requestservice.domain.integration.user.dto.PersonDto;
import com.example.requestservice.domain.integration.user.dto.UserDto;
import com.example.requestservice.domain.model.values.UserId;
import com.example.requestservice.domain.ports.out.UserServicePort;
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

    @Override
    public void verifyUserForBookItemRequest(UserId userId) {
        ResponseEntity<Void> response = client.verifyUserForBookItemRequest(userId.value());
        if (response.getStatusCode().is2xxSuccessful()) {

        }
        // TODO: 24.04.2025 jeśli nie to?
    }
}
