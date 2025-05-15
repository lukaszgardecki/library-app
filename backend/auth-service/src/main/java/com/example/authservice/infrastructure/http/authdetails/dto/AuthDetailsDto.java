package com.example.authservice.infrastructure.http.authdetails.dto;

public record AuthDetailsDto(
        Long id,
        String username,
        String role,
        String status,
        Long userId
) {

}
