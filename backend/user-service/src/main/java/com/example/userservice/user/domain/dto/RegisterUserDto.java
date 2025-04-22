package com.example.userservice.user.domain.dto;

import com.example.userservice.person.domain.model.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RegisterUserDto {
    private String firstName;
    private String lastName;

    private String pesel;
    private LocalDate dateOfBirth;
    private Gender gender;
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
