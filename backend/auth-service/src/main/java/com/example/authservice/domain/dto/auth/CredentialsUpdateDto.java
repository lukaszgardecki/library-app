package com.example.authservice.domain.dto.auth;

public record CredentialsUpdateDto(
        String username,
        String password
) {

}
