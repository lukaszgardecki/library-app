package com.example.libraryapp.domain.user.dto;

import com.example.libraryapp.domain.librarycard.model.LibraryCardStatus;
import com.example.libraryapp.domain.person.model.Gender;
import com.example.libraryapp.domain.user.model.AccountStatus;
import com.example.libraryapp.domain.user.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserUpdateAdminDto extends UserUpdate {
    private Gender gender;
    private String pesel;

    private LocalDate dateOfBirth;
    private String nationality;
    private String fathersName;
    private String mothersName;

    private AccountStatus accountStatus;
    private LibraryCardStatus cardStatus;
    private Role role;
}
