package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.LoanDueDate;
import com.example.catalogservice.domain.model.bookitem.UserId;
import lombok.Getter;

@Getter
public class BookItemRenewedEvent extends BookItemEvent {
    private final LoanDueDate dueDate;

    public BookItemRenewedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanDueDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
