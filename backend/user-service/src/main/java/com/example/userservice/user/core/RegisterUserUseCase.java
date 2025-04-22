package com.example.userservice.user.core;

import com.example.userservice.librarycard.core.LibraryCardFacade;
import com.example.userservice.person.core.PersonFacade;
import com.example.userservice.librarycard.domain.model.LibraryCardId;
import com.example.userservice.person.domain.dto.AddressDto;
import com.example.userservice.person.domain.dto.PersonDto;
import com.example.userservice.person.domain.model.PersonFirstName;
import com.example.userservice.person.domain.model.PersonId;
import com.example.userservice.person.domain.model.PersonLastName;
import com.example.userservice.user.domain.dto.RegisterUserDto;
import com.example.userservice.user.domain.model.user.*;
import com.example.userservice.user.domain.ports.UserRepositoryPort;
import com.example.userservice.user.domain.event.incoming.UserRegisteredEvent;
import com.example.userservice.user.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
class RegisterUserUseCase {
    private final UserRepositoryPort userRepository;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;
    private final EventPublisherPort publisher;

    public UserId execute(RegisterUserDto dto) {
        PersonDto personToSave = createPersonToSave(dto);
        PersonDto savedPerson = personFacade.save(personToSave);
        User userToSave = createUserToSave(dto);
        userToSave.setPersonId(new PersonId(savedPerson.getId()));
        User savedUser = userRepository.save(userToSave);
        LibraryCardId cardId = libraryCardFacade.createNewLibraryCard(savedUser.getId());
        savedUser.setCardId(cardId);
        userRepository.save(savedUser);
//        publisher.publish(
//                new UserRegisteredEvent(
//                        savedUser.getId(),
//                        new PersonFirstName(savedPerson.getFirstName()),
//                        new PersonLastName(savedPerson.getLastName())
//                )
//        );
        return savedUser.getId();
    }

    private User createUserToSave(RegisterUserDto dto) {
        return User.builder()
                .registrationDate(new RegistrationDate(LocalDate.now()))
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
