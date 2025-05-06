package com.example.authservice.domain.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDto {
    private String firstName;
    private String lastName;

    private String pesel;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String mothersName;
    private String fathersName;

    private String streetAddress;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    private String phone;
}
