package com.example.warehouseservice.domain.ports.out;

import com.example.warehouseservice.domain.integration.user.dto.PersonDto;
import com.example.warehouseservice.domain.integration.user.dto.UserDto;
import com.example.warehouseservice.domain.integration.user.UserId;

public interface UserServicePort {

    UserDto getUserById(UserId userId);

    PersonDto getPersonByUserId(UserId userId);

}
