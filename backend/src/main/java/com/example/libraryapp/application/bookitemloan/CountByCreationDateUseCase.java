package com.example.libraryapp.application.bookitemloan;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class CountByCreationDateUseCase {
    private final BookItemLoanService bookItemLoanService;

    long execute(LocalDateTime day) {
        return bookItemLoanService.countByCreationDate(day);
    }
}
