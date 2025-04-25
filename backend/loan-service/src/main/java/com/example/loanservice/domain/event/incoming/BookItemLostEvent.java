package com.example.loanservice.domain.event.incoming;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.model.*;
import lombok.Getter;

@Getter
public class BookItemLostEvent {
    private final LoanId id;
    private final LoanDueDate dueDate;
    private final LoanReturnDate returnDate;
    private final UserId userId;
    private final BookItemId bookItemId;
    private final Price charge;

    public BookItemLostEvent(BookItemLoanDto bookItemLoan, Price charge) {
        this.id = new LoanId(bookItemLoan.id());
        this.dueDate = new LoanDueDate(bookItemLoan.dueDate());
        this.returnDate = new LoanReturnDate(bookItemLoan.returnDate());
        this.userId = new UserId(bookItemLoan.userId());
        this.bookItemId = new BookItemId(bookItemLoan.bookItemId());
        this.charge = charge;
    }
}
