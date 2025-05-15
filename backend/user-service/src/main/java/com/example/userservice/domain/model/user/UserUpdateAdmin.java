package com.example.userservice.domain.model.user;

import com.example.userservice.domain.model.librarycard.values.LibraryCardStatus;
import com.example.userservice.domain.model.person.values.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class UserUpdateAdmin extends UserPerson {
    private Gender gender;
    private Pesel pesel;
    private BirthDate dateOfBirth;
    private Nationality nationality;
    private FatherName fathersName;
    private MotherName mothersName;
    private LibraryCardStatus cardStatus;
}
