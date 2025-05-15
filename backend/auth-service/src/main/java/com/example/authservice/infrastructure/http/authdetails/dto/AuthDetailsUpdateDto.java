package com.example.authservice.infrastructure.http.authdetails.dto;

public record AuthDetailsUpdateDto(
        String username,
        String password,
        String role,
        String status
) {

}
