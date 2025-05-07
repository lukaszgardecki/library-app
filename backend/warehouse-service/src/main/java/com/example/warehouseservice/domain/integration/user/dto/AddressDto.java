package com.example.warehouseservice.domain.integration.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
