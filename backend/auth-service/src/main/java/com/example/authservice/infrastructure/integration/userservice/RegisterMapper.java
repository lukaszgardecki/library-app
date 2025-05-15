package com.example.authservice.infrastructure.integration.userservice;

import com.example.authservice.domain.model.person.Person;
import com.example.authservice.infrastructure.integration.userservice.dto.RegisterUserDto;

class RegisterMapper {

    static RegisterUserDto toDto(Person userData) {
        return new RegisterUserDto(
                userData.getFirstName().value(),
                userData.getLastName().value(),
                userData.getPesel().value(),
                userData.getDateOfBirth().value(),
                userData.getGender().name(),
                userData.getNationality().value(),
                userData.getMothersName().value(),
                userData.getFathersName().value(),
                userData.getAddress().getStreetAddress().value(),
                userData.getAddress().getZipCode().value(),
                userData.getAddress().getCity().value(),
                userData.getAddress().getState().value(),
                userData.getAddress().getCountry().value(),
                userData.getPhone().value()
        );
    }
}
