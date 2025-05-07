package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.model.LoanId;
import com.example.catalogservice.domain.model.LoanReturnDate;
import com.example.catalogservice.domain.model.RequestId;
import com.example.catalogservice.domain.model.UserId;
import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.LoanCreationDate;
import com.example.catalogservice.domain.model.bookitem.LoanDueDate;
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
}

