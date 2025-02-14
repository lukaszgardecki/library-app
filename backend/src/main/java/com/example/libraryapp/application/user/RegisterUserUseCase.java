package com.example.libraryapp.application.user;

import com.example.libraryapp.application.librarycard.LibraryCardFacade;
import com.example.libraryapp.application.person.PersonFacade;
import com.example.libraryapp.domain.person.dto.AddressDto;
import com.example.libraryapp.domain.person.dto.PersonDto;
import com.example.libraryapp.domain.user.dto.RegisterUserDto;
import com.example.libraryapp.domain.user.model.AccountStatus;
import com.example.libraryapp.domain.user.model.Role;
import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.ports.UserRepository;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;
    private final UserCredentialsService credentialsService;
    private final EventPublisherPort publisher;

    public Long execute(RegisterUserDto dto) {
        credentialsService.validateEmail(dto.getEmail());
        PersonDto personToSave = createPersonToSave(dto);
        PersonDto savedPerson = personFacade.save(personToSave);
        User userToSave = createUserToSave(dto);
        userToSave.setPersonId(savedPerson.getId());
        User savedUser = userRepository.save(userToSave);
        Long cardId = libraryCardFacade.createNewLibraryCard(savedUser.getId());
        savedUser.setCardId(cardId);
        userRepository.save(savedUser);
        publisher.publish(new UserRegisteredEvent(savedUser.getId(), savedPerson.getFirstName(), savedPerson.getLastName()));
        return savedUser.getId();
    }

    private User createUserToSave(RegisterUserDto dto) {
        return User.builder()
                .registrationDate(LocalDateTime.now())
                .password(credentialsService.encodePassword(dto.getPassword()))
                .email(dto.getEmail())
                .status(AccountStatus.PENDING)
                .role(Role.USER)
                .totalBooksBorrowed(0)
                .totalBooksRequested(0)
                .charge(BigDecimal.ZERO)
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
