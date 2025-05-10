package com.example.requestservice.domain.event.incoming;

import com.example.requestservice.domain.integration.catalog.Subject;
import com.example.requestservice.domain.integration.catalog.Title;
import com.example.requestservice.domain.integration.loan.LoanCreationDate;
import com.example.requestservice.domain.integration.loan.LoanDueDate;
import com.example.requestservice.domain.integration.loan.LoanId;
import com.example.requestservice.domain.integration.loan.LoanReturnDate;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.RequestId;
import com.example.requestservice.domain.model.values.UserId;
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

