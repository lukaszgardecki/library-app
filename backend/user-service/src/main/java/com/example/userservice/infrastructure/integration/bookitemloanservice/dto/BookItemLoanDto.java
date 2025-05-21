package com.example.userservice.infrastructure.integration.bookitemloanservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record BookItemLoanDto(
        Long id,
        LocalDateTime creationDate,
        LocalDate dueDate,
        LocalDateTime returnDate,
        String status,
        Long userId,
        Long bookItemId
) { }
