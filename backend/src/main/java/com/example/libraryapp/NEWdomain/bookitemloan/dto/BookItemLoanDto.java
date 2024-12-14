package com.example.libraryapp.NEWdomain.bookitemloan.dto;

import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;

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
