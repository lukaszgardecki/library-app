package com.example.libraryapp.application.user;

import com.example.libraryapp.domain.user.dto.*;
import com.example.libraryapp.domain.user.model.*;

class UserMapper {

    static User toModel(UserDto user) {
        return User.builder()
                .id(user.getId())
                .registrationDate(user.getRegistrationDate())
                .email(user.getEmail())
                .status(user.getStatus())
                .role(user.getRole())
                .totalBooksBorrowed(user.getTotalBooksBorrowed())
                .totalBooksRequested(user.getTotalBooksReserved())
                .charge(user.getCharge())
                .cardId(user.getCardId())
                .personId(user.getPersonId())
                .build();
    }

    static UserDto toDto(User model) {
        return UserDto.builder()
                .id(model.getId())
                .registrationDate(model.getRegistrationDate())
                .email(model.getEmail())
                .status(model.getStatus())
                .role(model.getRole())
                .totalBooksBorrowed(model.getTotalBooksBorrowed())
                .totalBooksReserved(model.getTotalBooksRequested())
                .charge(model.getCharge())
                .cardId(model.getCardId())
                .personId(model.getPersonId())
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
