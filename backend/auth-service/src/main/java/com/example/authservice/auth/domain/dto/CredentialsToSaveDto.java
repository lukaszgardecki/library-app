package com.example.authservice.auth.domain.dto;

public record CredentialsToSaveDto(
        String username,
        String password,
        Long userId
) {

}
