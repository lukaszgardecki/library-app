package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUserCurrentLoansUseCase {
    private final BookItemLoanRepositoryPort repository;

    List<BookItemLoan> execute(Long userId) {
        return repository.findAllCurrentLoansByUserId(userId);
    }
}
