package com.example.libraryapp.domain.user.mapper;

import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.dto.UserCredentialsDto;

public class UserCredentialsDtoMapper {

    public static UserCredentialsDto map(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        String role = user.getRole().getName();
        return new UserCredentialsDto(email, password, role);
    }
}
