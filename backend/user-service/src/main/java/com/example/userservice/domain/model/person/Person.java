package com.example.userservice.domain.model.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Person {
    private PersonId id;
    private PersonFirstName firstName;
    private PersonLastName lastName;
    private Gender gender;
    private Pesel pesel;
    private BirthDate dateOfBirth;
    private Nationality nationality;
    private FatherName fathersName;
    private MotherName mothersName;
    private Address address;
    private PhoneNumber phone;
}
