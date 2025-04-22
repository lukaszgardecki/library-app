package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountByCreationDateUseCase {
    private final BookItemLoanService bookItemLoanService;

    long execute(LoanCreationDate day) {
        return bookItemLoanService.countByCreationDate(day);
    }
}
