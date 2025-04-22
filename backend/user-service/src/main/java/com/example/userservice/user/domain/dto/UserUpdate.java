package com.example.userservice.user.domain.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
abstract class UserUpdate {
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
