package com.example.libraryapp.application.bookitemloan;

import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
class CountBookItemLoansMonthly {
    private final BookItemLoanService bookItemLoanService;

    List<Object[]> execute(LocalDate startDate, LocalDate endDate) {
        return bookItemLoanService.countBookItemLoansMonthly(startDate, endDate);
    }
}
