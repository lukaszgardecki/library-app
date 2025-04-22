package com.example.userservice.user.domain.dto;

import com.example.userservice.user.domain.model.bookitemloan.LoanStatus;

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
