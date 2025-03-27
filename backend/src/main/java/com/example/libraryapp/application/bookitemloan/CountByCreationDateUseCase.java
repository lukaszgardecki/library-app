package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class CountByCreationDateUseCase {
    private final BookItemLoanService bookItemLoanService;

    long execute(LoanCreationDate day) {
        return bookItemLoanService.countByCreationDate(day);
    }
}
