package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.auth.ports.PasswordEncoderPort;
import com.example.libraryapp.NEWdomain.user.model.AccountStatus;
import com.example.libraryapp.NEWdomain.user.dto.RegisterUserDto;
import com.example.libraryapp.NEWdomain.user.model.Role;
import com.example.libraryapp.NEWdomain.user.model.Address;
import com.example.libraryapp.NEWdomain.user.model.Person;
import com.example.libraryapp.NEWdomain.user.model.User;
import com.example.libraryapp.NEWdomain.user.ports.PersonRepository;
import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserRegisteredEvent;
import com.example.libraryapp.NEWinfrastructure.events.publishers.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RequiredArgsConstructor
class RegisterUserUseCase {
    private final UserRepository userRepository;
    private final LibraryCardServiceService libraryCardInputService;
    private final PersonRepository personRepository;
    private final PasswordEncoderPort passwordEncoder;
    private final EventPublisherPort publisher;

    @Transactional
    public Long execute(RegisterUserDto dto) {
        boolean emailExists = userRepository.existsByEmail(dto.getEmail());
        if (emailExists) {
            throw new BadCredentialsException("Message.VALIDATION_EMAIL_UNIQUE.getMessage()");
        }
        Long cardId = libraryCardInputService.createLibraryCard();
        Person personToSave = createPersonToSave(dto);
        Person savedPerson = personRepository.save(personToSave);
        User userToSave = createUserToSave(dto);
        userToSave.setCardId(cardId);
        userToSave.setPersonId(savedPerson.getId());
        User savedUser = userRepository.save(userToSave);
        publisher.publish(new UserRegisteredEvent(savedUser.getId(), "jakaś wiadomosć"));
        return savedUser.getId();
    }

    private User createUserToSave(RegisterUserDto dto) {
        return User.builder()
                .registrationDate(LocalDateTime.now())
                .password(passwordEncoder.encode(dto.getPassword()))
                .email(dto.getEmail())
                .status(AccountStatus.PENDING)
                .role(Role.USER)
                .totalBooksBorrowed(0)
                .totalBooksRequested(0)
                .charge(BigDecimal.ZERO)
                .build();
    }

    private Person createPersonToSave(RegisterUserDto dto) {
        return Person.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .gender(dto.getGender())
                .pesel(dto.getPesel())
                .dateOfBirth(dto.getDateOfBirth())
                .nationality(dto.getNationality())
                .fathersName(dto.getFathersName())
                .mothersName(dto.getMothersName())
                .address(
                        Address.builder()
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
