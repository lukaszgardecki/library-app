package com.example.libraryapp.domain.user.mapper;

import com.example.libraryapp.domain.user.User;
import com.example.libraryapp.domain.user.dto.UserCredentialsDto;

public class UserCredentialsDtoMapper {

    public static UserCredentialsDto map(User user) {
        Long userId = user.getId();
        String email = user.getEmail();
        String password = user.getPassword();
        String role = user.getRole().getName();
        return new UserCredentialsDto(userId, email, password, role);
    }
}
