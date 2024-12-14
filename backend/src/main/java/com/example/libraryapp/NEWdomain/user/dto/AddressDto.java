package com.example.libraryapp.NEWdomain.user.dto;

import lombok.Builder;

@Builder
public record AddressDto(
        String streetAddress,
        String city,
        String state,
        String zipCode,
        String country
) {
}
