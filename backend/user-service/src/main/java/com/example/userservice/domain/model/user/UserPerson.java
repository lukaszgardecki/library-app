package com.example.userservice.domain.model.user;

import com.example.userservice.domain.integration.auth.Email;
import com.example.userservice.domain.model.person.values.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public abstract class UserPerson {
    private PersonFirstName firstName;
    private PersonLastName lastName;
    private Email email;

    private StreetAddress streetAddress;
    private ZipCode zipCode;
    private City city;
    private State state;
    private Country country;

    private PhoneNumber phone;
}
