package com.example.loanservice.domain.event.outgoing;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.model.*;
import lombok.Getter;

@Getter
public class BookItemReturnedEvent {
    private final LoanId loanId;
    private final LoanDueDate loanDueDate;
    private final LoanReturnDate loanReturnDate;
    private final UserId userId;
    private final BookItemId bookItemId;

    public BookItemReturnedEvent(BookItemLoanDto bookItemLoan) {
        this.loanId = new LoanId(bookItemLoan.id());
        this.loanDueDate = new LoanDueDate(bookItemLoan.dueDate());
        this.loanReturnDate = new LoanReturnDate(bookItemLoan.returnDate());
        this.userId = new UserId(bookItemLoan.userId());
        this.bookItemId = new BookItemId(bookItemLoan.bookItemId());
    }
}
