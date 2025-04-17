package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetBookItemLoanUseCase {
    private final BookItemLoanService bookItemLoanService;

    BookItemLoan execute(LoanId id) {
        return bookItemLoanService.getBookItemLoanById(id);
    }
}
