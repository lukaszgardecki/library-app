package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.IsReferenceOnly;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.user.model.UserId;
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

