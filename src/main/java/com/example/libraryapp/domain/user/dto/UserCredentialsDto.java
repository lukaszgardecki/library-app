package com.example.libraryapp.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserCredentialsDto {
    private final Long userId;
    private final String email;
    private final String password;
    private final String role;
}