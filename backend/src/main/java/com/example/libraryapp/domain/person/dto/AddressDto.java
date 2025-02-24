package com.example.libraryapp.domain.person.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class AddressDto {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
