package com.example.warehouseservice.domain.ports;

import com.example.warehouseservice.domain.dto.PersonDto;
import com.example.warehouseservice.domain.dto.UserDto;
import com.example.warehouseservice.domain.model.UserId;

public interface UserServicePort {

    UserDto getUserById(UserId userId);

    PersonDto getPersonByUserId(UserId userId);

}
