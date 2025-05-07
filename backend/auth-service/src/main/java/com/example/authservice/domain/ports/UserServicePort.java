package com.example.authservice.domain.ports;

import com.example.authservice.domain.dto.PersonDto;
import com.example.authservice.domain.dto.auth.RegisterUserDto;
import com.example.authservice.domain.model.authdetails.UserId;

public interface UserServicePort {

    UserId register(RegisterUserDto userData);

    PersonDto getPersonByUser(Long userId);
}
