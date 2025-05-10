package com.example.userservice.domain.event.incoming;

import com.example.userservice.domain.integration.catalog.Subject;
import com.example.userservice.domain.integration.request.RequestId;
import com.example.userservice.domain.integration.catalog.Title;
import com.example.userservice.domain.integration.catalog.BookItemId;
import com.example.userservice.domain.integration.loan.LoanCreationDate;
import com.example.userservice.domain.integration.loan.LoanDueDate;
import com.example.userservice.domain.integration.loan.LoanId;
import com.example.userservice.domain.integration.loan.LoanReturnDate;
import com.example.userservice.domain.model.user.values.UserId;
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

