package com.example.libraryapp.application.fine;

import com.example.libraryapp.domain.bookitem.model.Price;
import com.example.libraryapp.domain.bookitemloan.dto.BookItemLoanDto;
import com.example.libraryapp.domain.bookitemloan.model.LoanId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
class ProcessBookItemLostUseCase {
    private final FineService fineService;

    void execute(BookItemLoanDto bookItemLoan, Price bookItemPrice) {
        fineService.processFineForBookLost(
                bookItemLoan.returnDate(),
                bookItemLoan.dueDate(),
                new UserId(bookItemLoan.userId()),
                new LoanId(bookItemLoan.id()),
                bookItemPrice
        );
    }
}
