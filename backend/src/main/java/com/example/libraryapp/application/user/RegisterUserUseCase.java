package com.example.libraryapp.application.user;

import com.example.libraryapp.application.librarycard.LibraryCardFacade;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import com.example.libraryapp.domain.person.dto.AddressDto;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.person.model.PersonFirstName;
import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.person.model.PersonLastName;
import com.example.libraryapp.domain.user.dto.RegisterUserDto;
import com.example.libraryapp.domain.user.model.*;
import com.example.libraryapp.domain.user.ports.UserRepositoryPort;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
class RegisterUserUseCase {
    private final UserRepositoryPort userRepository;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;
    private final UserCredentialsService credentialsService;
    private final EventPublisherPort publisher;

    public UserId execute(RegisterUserDto dto) {
        credentialsService.validateEmail(new Email(dto.getEmail()));
        PersonDto personToSave = createPersonToSave(dto);
        PersonDto savedPerson = personFacade.save(personToSave);
        User userToSave = createUserToSave(dto);
        userToSave.setPersonId(new PersonId(savedPerson.getId()));
        User savedUser = userRepository.save(userToSave);
        LibraryCardId cardId = libraryCardFacade.createNewLibraryCard(savedUser.getId());
        savedUser.setCardId(cardId);
        userRepository.save(savedUser);
        publisher.publish(
                new UserRegisteredEvent(
                        savedUser.getId(),
                        new PersonFirstName(savedPerson.getFirstName()),
                        new PersonLastName(savedPerson.getLastName())
                )
        );
        return savedUser.getId();
    }

    private User createUserToSave(RegisterUserDto dto) {
        return User.builder()
                .registrationDate(new RegistrationDate(LocalDate.now()))
                .psswrd(new Password(credentialsService.encodePassword(dto.getPassword())))
                .email(new Email(dto.getEmail()))
                .status(AccountStatus.PENDING)
                .role(Role.USER)
                .totalBooksBorrowed(new TotalBooksBorrowed(0))
                .totalBooksRequested(new TotalBooksRequested(0))
                .charge(new UserCharge(BigDecimal.ZERO))
                .build();
    }

    private PersonDto createPersonToSave(RegisterUserDto dto) {
        return PersonDto.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .pesel(dto.getPesel())
                .dateOfBirth(dto.getDateOfBirth())
                .nationality(dto.getNationality())
                .fathersName(dto.getFathersName())
                .mothersName(dto.getMothersName())
                .address(
                        AddressDto.builder()
                                .streetAddress(dto.getStreetAddress())
                                .zipCode(dto.getZipCode())
                                .city(dto.getCity())
                                .state(dto.getState())
                                .country(dto.getCountry())
                                .build()
                )
                .phone(dto.getPhone())
                .build();
    }
}
