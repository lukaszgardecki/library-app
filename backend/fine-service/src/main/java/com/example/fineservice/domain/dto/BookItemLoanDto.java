package com.example.fineservice.domain.dto;

import com.example.fineservice.domain.model.LoanStatus;

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
