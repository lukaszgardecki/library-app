package com.example.libraryapp.domain.user.model;

import com.example.userservice.common.librarycard.model.LibraryCardId;
import com.example.userservice.common.person.model.PersonId;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UserId id;
    private RegistrationDate registrationDate;
    private Password psswrd;
    private Email email;
    private AccountStatus status;
    private Role role;
    private TotalBooksBorrowed totalBooksBorrowed;
    private TotalBooksRequested totalBooksRequested;
    private UserCharge charge;
    private LibraryCardId cardId;
    private PersonId personId;
}
