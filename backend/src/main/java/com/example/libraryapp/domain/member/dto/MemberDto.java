package com.example.libraryapp.domain.member.dto;

import com.example.libraryapp.domain.card.LibraryCard;
import com.example.libraryapp.domain.member.AccountStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class MemberDto extends RepresentationModel<MemberDto> {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LibraryCard card;
    private LocalDate dateOfMembership;
    private int totalBooksBorrowed;
    private int totalBooksReserved;
    private BigDecimal charge;
    private AccountStatus status;
}
