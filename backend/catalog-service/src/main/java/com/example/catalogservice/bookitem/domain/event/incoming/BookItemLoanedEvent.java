package com.example.catalogservice.bookitem.domain.event.incoming;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.bookitem.domain.model.*;
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

