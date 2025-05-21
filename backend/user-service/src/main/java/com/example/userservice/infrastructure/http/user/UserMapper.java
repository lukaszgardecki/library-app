package com.example.userservice.infrastructure.http.user;

import com.example.userservice.domain.integration.auth.Email;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.person.values.*;
import com.example.userservice.domain.model.user.User;
import com.example.userservice.domain.model.user.UserUpdate;
import com.example.userservice.domain.model.user.values.*;
import com.example.userservice.infrastructure.http.user.dto.UserDto;
import com.example.userservice.infrastructure.http.user.dto.UserUpdateDto;

class UserMapper {

    static User toModel(UserDto user) {
        return User.builder()
                .id(new UserId(user.getId()))
                .registrationDate(new RegistrationDate(user.getRegistrationDate()))
                .totalBooksBorrowed(new TotalBooksBorrowed(user.getTotalBooksBorrowed()))
                .totalBooksRequested(new TotalBooksRequested(user.getTotalBooksReserved()))
                .charge(new UserCharge(user.getCharge()))
                .cardId(new LibraryCardId(user.getCardId()))
                .personId(new PersonId(user.getPersonId()))
                .build();
    }

    static UserDto toDto(User model) {
        return UserDto.builder()
                .id(model.getId().value())
                .registrationDate(model.getRegistrationDate().value())
                .totalBooksBorrowed(model.getTotalBooksBorrowed().value())
                .totalBooksReserved(model.getTotalBooksRequested().value())
                .charge(model.getCharge().value())
                .cardId(model.getCardId().value())
                .personId(model.getPersonId().value())
                .build();
    }

    static UserUpdate toModel(UserUpdateDto dto) {
        return UserUpdate.builder()
                .firstName(new PersonFirstName(dto.getFirstName()))
                .lastName(new PersonLastName(dto.getLastName()))
                .email(new Email(dto.getEmail()))
                .streetAddress(new StreetAddress(dto.getStreetAddress()))
                .zipCode(new ZipCode(dto.getZipCode()))
                .city(new City(dto.getCity()))
                .state(new State(dto.getState()))
                .country(new Country(dto.getCountry()))
                .phone(new PhoneNumber(dto.getPhone()))
                .build();
    }
}
