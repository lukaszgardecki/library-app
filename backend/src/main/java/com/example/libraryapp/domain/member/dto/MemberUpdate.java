package com.example.libraryapp.domain.member.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class MemberUpdate {
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
