package com.example.userservice.infrastructure.integration.authservice.dto;

public record AuthDetailsDto(
        Long id,
        String username,
        String role,
        String status,
        Long userId
) {

}
