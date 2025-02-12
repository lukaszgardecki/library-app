package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
class CountBookItemLoansDaily {
    private final BookItemLoanService bookItemLoanService;

    List<Object[]> execute(LocalDate startDate, LocalDate endDate, BookItemLoanStatus status) {
        return bookItemLoanService.countBookItemLoansDaily(startDate, endDate, status);
    }
}
