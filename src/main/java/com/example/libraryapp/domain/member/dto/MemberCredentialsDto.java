package com.example.libraryapp.domain.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberCredentialsDto {
    private final Long userId;
    private final String email;
    private final String password;
    private final String role;
}