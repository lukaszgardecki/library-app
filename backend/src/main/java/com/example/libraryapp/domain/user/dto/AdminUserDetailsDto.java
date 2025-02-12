package com.example.libraryapp.domain.user.dto;

import com.example.libraryapp.domain.librarycard.dto.LibraryCardDto;
import com.example.libraryapp.domain.person.model.Gender;
import com.example.libraryapp.domain.user.model.AccountStatus;
import com.example.libraryapp.domain.user.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class AdminUserDetailsDto {
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
    private LocalDateTime dateOfMembership;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;
    private AccountStatus status;
    private List<Long> loanedItemsIds;
    private List<Long> requestedItemsIds;
    private Role role;

    private Map<String, Integer> genresStats;
    private List<Integer> loansPerMonth;
}
