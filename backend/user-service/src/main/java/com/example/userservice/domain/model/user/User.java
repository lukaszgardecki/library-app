package com.example.userservice.domain.model.user;

import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.values.*;
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
