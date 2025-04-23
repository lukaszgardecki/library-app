package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.model.book.Title;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitem.IsReferenceOnly;
import com.example.userservice.domain.model.bookitemloan.LoanCreationDate;
import com.example.userservice.domain.model.bookitemloan.LoanDueDate;
import lombok.Getter;

@Getter
public class BookItemLoanedEvent extends BookItemEvent {
    private final LoanCreationDate creationDate;
    private final LoanDueDate dueDate;
    private final IsReferenceOnly isReferenceOnly;
    
    public BookItemLoanedEvent(
            BookItemId bookItemId, UserId userId, Title bookTitle, IsReferenceOnly isReferenceOnly,
            LoanCreationDate creationDate, LoanDueDate dueDate
    ) {
        super(bookItemId, userId, bookTitle);
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.isReferenceOnly = isReferenceOnly;
    }
}

