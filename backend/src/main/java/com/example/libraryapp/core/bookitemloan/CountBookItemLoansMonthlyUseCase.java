package com.example.libraryapp.core.bookitemloan;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
class CountBookItemLoansMonthlyUseCase {
    private final BookItemLoanService bookItemLoanService;

    List<Object[]> execute(LocalDate startDate, LocalDate endDate) {
        return bookItemLoanService.countBookItemLoansMonthly(startDate, endDate);
    }
}
