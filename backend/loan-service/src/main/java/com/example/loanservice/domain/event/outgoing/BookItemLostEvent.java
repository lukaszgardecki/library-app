package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.model.*;
import lombok.Getter;

@Getter
public class BookItemLostEvent {
    private final LoanId loanId;
    private final LoanDueDate loanDueDate;
    private final LoanReturnDate loanReturnDate;
    private final UserId userId;
    private final BookItemId bookItemId;
    private final Price charge;

    public BookItemLostEvent(BookItemLoanDto bookItemLoan, Price charge) {
        this.loanId = new LoanId(bookItemLoan.id());
        this.loanDueDate = new LoanDueDate(bookItemLoan.dueDate());
        this.loanReturnDate = new LoanReturnDate(bookItemLoan.returnDate());
        this.userId = new UserId(bookItemLoan.userId());
        this.bookItemId = new BookItemId(bookItemLoan.bookItemId());
        this.charge = charge;
    }
}
