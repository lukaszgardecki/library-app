package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.model.*;
import lombok.Getter;

@Getter
public class LoanCreatedEvent {
    private final LoanId loanId;
    private final LoanCreationDate loanCreationDate;
    private final LoanDueDate loanDueDate;
    private final LoanReturnDate loanReturnDate;
    private final UserId userId;
    private final BookItemId bookItemId;
    private final RequestId requestId;
    
    public LoanCreatedEvent(BookItemLoanDto bookItemLoan, RequestId requestId) {
        this.loanId = new LoanId(bookItemLoan.id());
        this.loanCreationDate = new LoanCreationDate(bookItemLoan.creationDate());
        this.loanDueDate = new LoanDueDate(bookItemLoan.dueDate());
        this.loanReturnDate = new LoanReturnDate(bookItemLoan.returnDate());
        this.userId = new UserId(bookItemLoan.userId());
        this.bookItemId = new BookItemId(bookItemLoan.bookItemId());
        this.requestId = requestId;
    }
}

