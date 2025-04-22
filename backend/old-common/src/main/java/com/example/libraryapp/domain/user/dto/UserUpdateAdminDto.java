package com.example.libraryapp.domain.user.dto;

import com.example.userservice.common.librarycard.model.LibraryCardStatus;
import com.example.userservice.common.person.model.Gender;
import com.example.userservice.common.user.model.AccountStatus;
import com.example.userservice.common.user.model.Role;
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
