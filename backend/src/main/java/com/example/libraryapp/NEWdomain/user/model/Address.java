package com.example.libraryapp.NEWdomain.user.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Address {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
