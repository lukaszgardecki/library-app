package com.example.userservice.person.domain.dto;

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
