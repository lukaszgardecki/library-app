package com.example.catalogservice.bookitem.domain.event.incoming;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.LoanDueDate;
import com.example.catalogservice.bookitem.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRenewedEvent extends BookItemEvent {
    private final LoanDueDate dueDate;

    public BookItemRenewedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanDueDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
