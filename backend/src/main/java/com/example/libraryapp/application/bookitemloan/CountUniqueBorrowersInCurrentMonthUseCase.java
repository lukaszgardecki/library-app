package com.example.libraryapp.application.bookitemloan;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CountUniqueBorrowersInCurrentMonthUseCase {
    private final BookItemLoanService bookItemLoanService;

    long execute() {
        return bookItemLoanService.countUniqueBorrowersInCurrentMonth();
    }
}
