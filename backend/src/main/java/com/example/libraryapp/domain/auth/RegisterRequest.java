package com.example.libraryapp.domain.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;

    private String streetAddress;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    private String phone;
}
