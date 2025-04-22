package com.example.userservice.user.domain.model.user;

import com.example.userservice.librarycard.domain.model.LibraryCardId;
import com.example.userservice.person.domain.model.PersonId;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UserId id;
    private RegistrationDate registrationDate;
    private TotalBooksBorrowed totalBooksBorrowed;
    private TotalBooksRequested totalBooksRequested;
    private UserCharge charge;
    private LibraryCardId cardId;
    private PersonId personId;
}
