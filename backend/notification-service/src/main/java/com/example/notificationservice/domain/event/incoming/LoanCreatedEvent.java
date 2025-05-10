package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.integration.catalog.BookItemId;
import com.example.notificationservice.domain.integration.catalog.Subject;
import com.example.notificationservice.domain.integration.catalog.Title;
import com.example.notificationservice.domain.integration.loan.LoanCreationDate;
import com.example.notificationservice.domain.integration.loan.LoanDueDate;
import com.example.notificationservice.domain.integration.loan.LoanId;
import com.example.notificationservice.domain.integration.loan.LoanReturnDate;
import com.example.notificationservice.domain.integration.request.RequestId;
import com.example.notificationservice.domain.model.values.UserId;
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

