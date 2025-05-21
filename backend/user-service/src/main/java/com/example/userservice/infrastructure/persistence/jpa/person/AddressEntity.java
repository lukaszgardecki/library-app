package com.example.userservice.infrastructure.persistence.jpa.person;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Table;
import lombok.*;

@Table(name = "address")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
class AddressEntity {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}

