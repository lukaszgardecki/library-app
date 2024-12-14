package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.dto.UserDto;
import com.example.libraryapp.NEWdomain.user.model.User;

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
}
