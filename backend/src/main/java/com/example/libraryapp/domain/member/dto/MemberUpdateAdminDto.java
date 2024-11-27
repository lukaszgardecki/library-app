package com.example.libraryapp.domain.member.dto;

import com.example.libraryapp.domain.card.CardStatus;
import com.example.libraryapp.domain.member.AccountStatus;
import com.example.libraryapp.domain.member.Gender;
import com.example.libraryapp.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberUpdateAdminDto extends MemberUpdate {
    private Gender gender;
    private String pesel;

    private LocalDate dateOfBirth;
    private String nationality;
    private String fathersName;
    private String mothersName;

    private AccountStatus accountStatus;
    private CardStatus cardStatus;
    private Role role;
}
