package com.example.loanservice.domain.dto;

import com.example.loanservice.domain.model.values.LoanStatus;

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
