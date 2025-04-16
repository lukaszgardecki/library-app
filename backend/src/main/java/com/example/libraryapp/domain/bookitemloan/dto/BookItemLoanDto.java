package com.example.libraryapp.domain.bookitemloan.dto;

import com.example.libraryapp.domain.bookitemloan.model.LoanStatus;

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
