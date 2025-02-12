package com.example.libraryapp.domain.bookitemloan.dto;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;

import java.time.LocalDateTime;

public record BookItemLoanDto(
        Long id,
        LocalDateTime creationDate,
        LocalDateTime dueDate,
        LocalDateTime returnDate,
        BookItemLoanStatus status,
        Long userId,
        Long bookId,
        Long bookItemId
) { }
