package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.integration.loan.LoanId;
import com.example.catalogservice.domain.integration.loan.LoanReturnDate;
import com.example.catalogservice.domain.integration.request.RequestId;
import com.example.catalogservice.domain.integration.UserId;
import com.example.catalogservice.domain.model.book.values.Subject;
import com.example.catalogservice.domain.model.book.values.Title;
import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.LoanCreationDate;
import com.example.catalogservice.domain.model.bookitem.values.LoanDueDate;
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

