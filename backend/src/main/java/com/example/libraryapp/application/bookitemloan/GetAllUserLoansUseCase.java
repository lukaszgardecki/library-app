package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetAllUserLoansUseCase {
    private final BookItemLoanService bookItemLoanService;

    List<BookItemLoan> execute(UserId userId) {
        return bookItemLoanService.getAllBookItemLoans(userId);
    }
}
