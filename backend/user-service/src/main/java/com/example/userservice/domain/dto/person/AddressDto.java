package com.example.userservice.domain.dto.person;

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
