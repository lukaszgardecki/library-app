package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.LoanReturnDate;
import com.example.catalogservice.domain.model.bookitem.UserId;
import lombok.Getter;

@Getter
public class BookItemReturnedEvent extends BookItemEvent {
    private final LoanReturnDate dueDate;
    public BookItemReturnedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanReturnDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
