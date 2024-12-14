package com.example.libraryapp.NEWapplication.bookitemloan;

import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemLoanUseCase {
    private final BookItemLoanService bookItemLoanService;

    BookItemLoan execute(Long id) {
        return bookItemLoanService.getBookItemLoan(id);
    }
}
