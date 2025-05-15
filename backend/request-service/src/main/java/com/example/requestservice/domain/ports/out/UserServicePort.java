package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.integration.user.dto.PersonDto;
import com.example.requestservice.domain.integration.user.dto.UserDto;
import com.example.requestservice.domain.model.values.UserId;

public interface UserServicePort {
    UserDto getUserById(UserId userId);

    PersonDto getPersonByUserId(UserId userId);

    void verifyUserForBookItemRequest(UserId userId);
}
