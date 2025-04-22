package com.example.catalogservice.bookitem.domain.event.incoming;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.LoanReturnDate;
import com.example.catalogservice.bookitem.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemReturnedEvent extends BookItemEvent {
    private final LoanReturnDate dueDate;
    public BookItemReturnedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanReturnDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
