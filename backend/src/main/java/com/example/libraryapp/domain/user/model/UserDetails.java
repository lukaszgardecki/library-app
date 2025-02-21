package com.example.libraryapp.domain.user.model;

import com.example.libraryapp.domain.librarycard.dto.LibraryCardDto;
import com.example.libraryapp.domain.person.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class UserDetails {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;

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
    private AccountStatus status;
}
