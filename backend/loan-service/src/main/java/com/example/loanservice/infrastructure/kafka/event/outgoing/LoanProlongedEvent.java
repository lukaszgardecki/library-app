package com.example.loanservice.infrastructure.kafka.event.outgoing;

import com.example.loanservice.infrastructure.http.dto.BookItemLoanDto;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class LoanProlongedEvent {
    private final Long loanId;
    private final LocalDate loanDueDate;
    private final LocalDateTime loanReturnDate;
    private final Long userId;
    private final Long bookItemId;
    private final String bookTitle;

    public LoanProlongedEvent(BookItemLoanDto bookItemLoan, String bookTitle) {
        this.loanId = bookItemLoan.id();
        this.loanDueDate = bookItemLoan.dueDate();
        this.loanReturnDate = bookItemLoan.returnDate();
        this.userId = bookItemLoan.userId();
        this.bookItemId = bookItemLoan.bookItemId();
        this.bookTitle = bookTitle;
    }
}
