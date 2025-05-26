package com.example.loanservice.infrastructure.kafka.event.outgoing;

import com.example.loanservice.infrastructure.http.dto.BookItemLoanDto;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class LoanCreatedEvent {
    private final Long loanId;
    private final LocalDateTime loanCreationDate;
    private final LocalDate loanDueDate;
    private final LocalDateTime loanReturnDate;
    private final Long userId;
    private final Long bookItemId;
    private final Long requestId;
    private final Boolean isReferenceOnly;
    private final String bookTitle;
    private final String bookSubject;
    
    public LoanCreatedEvent(
            BookItemLoanDto bookItemLoan, Long requestId, Boolean isReferenceOnly, String bookTitle, String subject
    ) {
        this.loanId = bookItemLoan.id();
        this.loanCreationDate = bookItemLoan.creationDate();
        this.loanDueDate = bookItemLoan.dueDate();
        this.loanReturnDate = bookItemLoan.returnDate();
        this.userId = bookItemLoan.userId();
        this.bookItemId = bookItemLoan.bookItemId();
        this.requestId = requestId;
        this.isReferenceOnly = isReferenceOnly;
        this.bookTitle = bookTitle;
        this.bookSubject = subject;
    }
}

