package com.example.userservice.domain.model.person;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class Address {
    private StreetAddress streetAddress;
    private City city;
    private State state;
    private ZipCode zipCode;
    private Country country;

    public record StreetAddress(String value) { }
    public record City(String value) { }
    public record State(String value) { }
    public record ZipCode(String value) { }
    public record Country(String value) { }
}
