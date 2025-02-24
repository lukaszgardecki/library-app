package com.example.libraryapp.domain.person.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Person {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String pesel;
    private LocalDate dateOfBirth;
    private String nationality;
    private String fathersName;
    private String mothersName;
    private Address address;
    private String phone;
}
