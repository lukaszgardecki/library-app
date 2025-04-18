package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import com.example.libraryapp.domain.person.model.PersonId;
import com.example.libraryapp.domain.user.dto.*;
import com.example.libraryapp.domain.user.model.*;

class UserMapper {

    static User toModel(UserDto user) {
        return User.builder()
                .id(new UserId(user.getId()))
                .registrationDate(new RegistrationDate(user.getRegistrationDate()))
                .email(new Email(user.getEmail()))
                .status(user.getStatus())
                .role(user.getRole())
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
                .email(model.getEmail().value())
                .status(model.getStatus())
                .role(model.getRole())
                .totalBooksBorrowed(model.getTotalBooksBorrowed().value())
                .totalBooksReserved(model.getTotalBooksRequested().value())
                .charge(model.getCharge().value())
                .cardId(model.getCardId().value())
                .personId(model.getPersonId().value())
                .build();
    }

    static UserPreviewDto toDto(UserPreview model) {
        return new UserPreviewDto(
                model.getId(),
                model.getFirstName(),
                model.getLastName(),
                model.getRole()
        );
    }

    static UserListPreviewDto toDto(UserListPreviewProjection model) {
        return new UserListPreviewDto(
                model.getId(),
                model.getFirstName(),
                model.getLastName(),
                model.getEmail(),
                model.getRegistrationDate(),
                model.getStatus()
        );
    }

    static UserDetailsDto toDto(UserDetails model) {
        return new UserDetailsDto(
                model.getId(),
                model.getFirstName(),
                model.getLastName(),
                model.getGender(),
                model.getStreetAddress(),
                model.getZipCode(),
                model.getCity(),
                model.getState(),
                model.getCountry(),
                model.getDateOfBirth(),
                model.getEmail(),
                model.getPhoneNumber(),
                model.getPesel(),
                model.getNationality(),
                model.getFathersName(),
                model.getMothersName(),
                model.getCard(),
                model.getDateOfMembership(),
                model.getTotalBooksBorrowed(),
                model.getTotalBooksReserved(),
                model.getCharge(),
                model.getStatus()
        );
    }

    static UserDetailsAdminDto toDto(UserDetailsAdmin model) {
        return new UserDetailsAdminDto(
                model.getId(),
                model.getFirstName(),
                model.getLastName(),
                model.getGender(),
                model.getStreetAddress(),
                model.getZipCode(),
                model.getCity(),
                model.getState(),
                model.getCountry(),
                model.getDateOfBirth(),
                model.getEmail(),
                model.getPhoneNumber(),
                model.getPesel(),
                model.getNationality(),
                model.getFathersName(),
                model.getMothersName(),
                model.getCard(),
                model.getDateOfMembership(),
                model.getTotalBooksBorrowed(),
                model.getTotalBooksReserved(),
                model.getCharge(),
                model.getStatus(),
                model.getLoanedItemsIds(),
                model.getRequestedItemsIds(),
                model.getRole(),
                model.getGenresStats(),
                model.getLoansPerMonth()
        );
    }
}
