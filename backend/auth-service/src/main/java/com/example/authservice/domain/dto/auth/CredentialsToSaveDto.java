package com.example.authservice.domain.dto.auth;

public record CredentialsToSaveDto(
        String username,
        String password,
        Long userId
) {

}
