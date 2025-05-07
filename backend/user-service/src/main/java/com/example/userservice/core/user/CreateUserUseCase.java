package com.example.userservice.core.user;

import com.example.userservice.core.librarycard.LibraryCardFacade;
import com.example.userservice.core.person.PersonFacade;
import com.example.userservice.domain.dto.person.AddressDto;
import com.example.userservice.domain.dto.person.PersonDto;
import com.example.userservice.domain.dto.user.RegisterUserDto;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.person.values.PersonId;
import com.example.userservice.domain.model.user.values.*;
import com.example.userservice.domain.ports.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@RequiredArgsConstructor
class CreateUserUseCase {
    private final UserRepositoryPort userRepository;
    private final PersonFacade personFacade;
    private final LibraryCardFacade libraryCardFacade;

    public UserId execute(RegisterUserDto dto) {
        PersonDto personToSave = createPersonToSave(dto);
        PersonDto savedPerson = personFacade.save(personToSave);
        User userToSave = createUserToSave(savedPerson.getId());
        User savedUser = userRepository.save(userToSave);
        LibraryCardId cardId = libraryCardFacade.createNewLibraryCard(savedUser.getId());
        savedUser.setCardId(cardId);
        userRepository.save(savedUser);
        return savedUser.getId();
    }

    private User createUserToSave(Long personId) {
        return User.builder()
                .registrationDate(new RegistrationDate(LocalDate.now()))
                .totalBooksBorrowed(new TotalBooksBorrowed(0))
                .totalBooksRequested(new TotalBooksRequested(0))
                .charge(new UserCharge(BigDecimal.ZERO))
                .personId(new PersonId(personId))
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
