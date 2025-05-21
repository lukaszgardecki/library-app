package com.example.authservice.infrastructure.http.register.dto;

import java.time.LocalDate;

public record RegisterToSaveDto(
        String username,
        String password,

        String firstName,
        String lastName,
        String pesel,
        LocalDate dateOfBirth,
        String gender,
        String nationality,
        String mothersName,
        String fathersName,
        String streetAddress,
        String zipCode,
        String city,
        String state,
        String country,
        String phone
) {

}
