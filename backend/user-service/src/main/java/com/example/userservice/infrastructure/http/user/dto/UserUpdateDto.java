package com.example.userservice.infrastructure.http.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDto {
    private String firstName;
    private String lastName;
    private String email;

    private String streetAddress;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    private String phone;
}
