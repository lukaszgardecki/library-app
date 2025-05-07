package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.integration.catalog.Title;
import com.example.loanservice.domain.integration.request.RequestId;
import com.example.loanservice.domain.model.values.*;
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
    private final Boolean isReferenceOnly;
    private final Title bookTitle;
    
    public LoanCreatedEvent(
            BookItemLoanDto bookItemLoan, RequestId requestId, Boolean isReferenceOnly, Title bookTitle
    ) {
        this.loanId = new LoanId(bookItemLoan.id());
        this.loanCreationDate = new LoanCreationDate(bookItemLoan.creationDate());
        this.loanDueDate = new LoanDueDate(bookItemLoan.dueDate());
        this.loanReturnDate = new LoanReturnDate(bookItemLoan.returnDate());
        this.userId = new UserId(bookItemLoan.userId());
        this.bookItemId = new BookItemId(bookItemLoan.bookItemId());
        this.requestId = requestId;
        this.isReferenceOnly = isReferenceOnly;
        this.bookTitle = bookTitle;
    }
}

