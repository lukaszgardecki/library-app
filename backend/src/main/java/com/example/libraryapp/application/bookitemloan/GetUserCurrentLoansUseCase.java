package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class GetUserCurrentLoansUseCase {
    private final BookItemLoanRepository repository;


    List<BookItemLoan> execute(Long userId) {
        return repository.findAllCurrentLoansByUserId(userId);
    }
}
