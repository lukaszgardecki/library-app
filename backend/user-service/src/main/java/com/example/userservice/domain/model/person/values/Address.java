package com.example.userservice.domain.model.person.values;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Address {
    private StreetAddress streetAddress;
    private City city;
    private State state;
    private ZipCode zipCode;
    private Country country;
}
