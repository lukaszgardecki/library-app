package com.example.libraryapp.core.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUserCurrentLoansUseCase {
    private final BookItemLoanRepositoryPort repository;

    List<BookItemLoan> execute(UserId userId) {
        return repository.findAllCurrentLoansByUserId(userId);
    }
}
