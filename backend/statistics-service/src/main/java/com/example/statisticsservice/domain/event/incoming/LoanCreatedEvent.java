package com.example.statisticsservice.domain.event.incoming;

import com.example.statisticsservice.domain.integration.*;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LoanCreatedEvent {
    private LoanId loanId;
    private LoanCreationDate loanCreationDate;
    private LoanDueDate loanDueDate;
    private LoanReturnDate loanReturnDate;
    private UserId userId;
    private BookItemId bookItemId;
    private RequestId requestId;
    private Boolean isReferenceOnly;
    private Title bookTitle;
    private Subject bookSubject;
}

