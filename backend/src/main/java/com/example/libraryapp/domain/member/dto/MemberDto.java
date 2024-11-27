package com.example.libraryapp.domain.member.dto;

import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.member.AccountStatus;
import com.example.libraryapp.domain.member.Gender;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class MemberDto extends RepresentationModel<MemberDto> {
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private String address;
    private LocalDate dateOfBirth;
    private String email;
    private String phoneNumber;
    private String pesel;
    private String nationality;
    private String parentsNames;
    private LibraryCard card;
    private LocalDate dateOfMembership;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;
    private AccountStatus status;
    private List<Long> loanedItemsIds;
    private List<Long> reservedItemsIds;
}
