package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemLoanUseCase {
    private final BookItemLoanService bookItemLoanService;

    BookItemLoan execute(Long id) {
        return bookItemLoanService.getBookItemLoanById(id);
    }
}
