package com.example.libraryapp.NEWdomain.user.dto;

import com.example.libraryapp.NEWdomain.user.model.Gender;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record PersonDto(
        Long id,
        String firstName,
        String lastName,
        Gender gender,
        String pesel,
        LocalDate dateOfBirth,
        String nationality,
        String fathersName,
        String mothersName,
        AddressDto address,
        String phone
) {
}
