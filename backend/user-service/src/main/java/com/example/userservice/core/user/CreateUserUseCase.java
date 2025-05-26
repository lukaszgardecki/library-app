package com.example.userservice.core.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.values.*;
import com.example.userservice.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
class CreateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final LibraryCardFacade libraryCardFacade;

    public UserId execute(PersonId personId) {
        User userToSave = createUserToSave(personId);
        User savedUser = userRepository.save(userToSave);
        LibraryCardId cardId = libraryCardFacade.createNewLibraryCard(savedUser.getId());
        savedUser.setCardId(cardId);
        userRepository.save(savedUser);
        return savedUser.getId();
    }

    private User createUserToSave(PersonId personId) {
        return User.builder()
                .registrationDate(new RegistrationDate(LocalDate.now()))
                .totalBooksBorrowed(new TotalBooksBorrowed(0))
                .totalBooksRequested(new TotalBooksRequested(0))
                .charge(new UserCharge(BigDecimal.ZERO))
                .personId(personId)
                .build();
    }
}
