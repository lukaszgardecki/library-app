package com.example.libraryapp.OLDdomain.member.dto;

import com.example.libraryapp.OLDdomain.card.LibraryCard;
import com.example.libraryapp.NEWdomain.user.model.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MemberDtoAdmin extends RepresentationModel<MemberDtoAdmin> {
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

    private LibraryCard card;
    private LocalDate dateOfMembership;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;
    private AccountStatus status;
    private List<Long> loanedItemsIds;
    private List<Long> reservedItemsIds;
    private Role role;

    private Map<String, Integer> genresStats;
    private List<Integer> lendingsPerMonth;
}
