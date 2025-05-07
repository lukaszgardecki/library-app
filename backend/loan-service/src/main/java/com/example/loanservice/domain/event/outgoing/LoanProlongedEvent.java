package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.integration.catalog.Title;
import com.example.loanservice.domain.model.values.*;
import lombok.Getter;

@Getter
public class LoanProlongedEvent {
    private final LoanId loanId;
    private final LoanDueDate loanDueDate;
    private final LoanReturnDate loanReturnDate;
    private final UserId userId;
    private final BookItemId bookItemId;
    private final Title bookTitle;

    public LoanProlongedEvent(BookItemLoanDto bookItemLoan, Title bookTitle) {
        this.loanId = new LoanId(bookItemLoan.id());
        this.loanDueDate = new LoanDueDate(bookItemLoan.dueDate());
        this.loanReturnDate = new LoanReturnDate(bookItemLoan.returnDate());
        this.userId = new UserId(bookItemLoan.userId());
        this.bookItemId = new BookItemId(bookItemLoan.bookItemId());
        this.bookTitle = bookTitle;
    }
}
