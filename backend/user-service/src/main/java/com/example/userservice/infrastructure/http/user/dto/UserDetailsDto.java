package com.example.userservice.infrastructure.http.user.dto;

import com.example.userservice.infrastructure.http.librarycard.dto.LibraryCardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String gender;

    private String streetAddress;
    private String zipCode;
    private String city;
    private String state;
    private String country;

    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String pesel;
    private String nationality;

    private String fathersName;
    private String mothersName;

    private LibraryCardDto card;
    private LocalDate dateOfMembership;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;
    private String status;
}
