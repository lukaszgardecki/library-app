package com.example.userservice.domain.integration.loan.dto;

import com.example.userservice.domain.integration.loan.LoanStatus;

import java.time.LocalDateTime;

public record BookItemLoanDto(
        Long id,
        LocalDateTime creationDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        LoanStatus status,
        Long userId,
        Long bookItemId
) { }
