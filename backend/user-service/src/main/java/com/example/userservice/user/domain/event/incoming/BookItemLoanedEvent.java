package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.user.domain.model.book.Title;
import com.example.userservice.user.domain.model.bookitem.BookItemId;
import com.example.userservice.user.domain.model.bookitem.IsReferenceOnly;
import com.example.userservice.user.domain.model.bookitemloan.LoanCreationDate;
import com.example.userservice.user.domain.model.bookitemloan.LoanDueDate;
import com.example.userservice.user.domain.model.user.UserId;
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

