package com.example.authservice.infrastructure.http.authdetails.dto;

public record CredentialsUpdateDto(
        String username,
        String password
) {

}
