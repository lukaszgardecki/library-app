package com.example.loanservice.infrastructure.kafka.event.outgoing;

import com.example.loanservice.domain.model.values.LoanDueDate;
import com.example.loanservice.domain.model.values.LoanReturnDate;
import com.example.loanservice.infrastructure.http.dto.BookItemLoanDto;
import lombok.Getter;

@Getter
public class BookItemReturnedEvent {
    private final Long loanId;
    private final LoanDueDate loanDueDate;
    private final LoanReturnDate loanReturnDate;
    private final Long userId;
    private final Long bookItemId;
    private final String bookTitle;

    public BookItemReturnedEvent(BookItemLoanDto bookItemLoan, String bookTitle) {
        this.loanId = bookItemLoan.id();
        this.loanDueDate = new LoanDueDate(bookItemLoan.dueDate());
        this.loanReturnDate = new LoanReturnDate(bookItemLoan.returnDate());
        this.userId = bookItemLoan.userId();
        this.bookItemId = bookItemLoan.bookItemId();
        this.bookTitle = bookTitle;
    }
}
