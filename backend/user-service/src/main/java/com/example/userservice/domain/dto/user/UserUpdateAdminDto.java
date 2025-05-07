package com.example.userservice.domain.dto.user;

import com.example.userservice.domain.model.librarycard.values.LibraryCardStatus;
import com.example.userservice.domain.model.person.values.Gender;
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
    private LibraryCardStatus cardStatus;
}
