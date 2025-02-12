package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class ProcessBookItemLostUseCase {
    private final FineService fineService;

    void execute(BookItemLoanDto bookItemLoan, BigDecimal bookItemPrice) {
        fineService.processFineForBookLost(
                bookItemLoan.returnDate(),
                bookItemLoan.dueDate(),
                bookItemLoan.userId(),
                bookItemLoan.id(),
                bookItemPrice
        );
    }
}
