package com.example.userservice.infrastructure.http.user.dto;

import com.example.userservice.infrastructure.http.librarycard.dto.LibraryCardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class UserDetailsAdminDto {
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
    private List<Long> loanedItemsIds;
    private List<Long> requestedItemsIds;
    private String role;

    private Map<String, Integer> genresStats;
    private List<Integer> loansPerMonth;
}
