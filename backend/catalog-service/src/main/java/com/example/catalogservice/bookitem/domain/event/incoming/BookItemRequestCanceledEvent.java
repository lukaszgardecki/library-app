package com.example.catalogservice.bookitem.domain.event.incoming;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestCanceledEvent extends BookItemEvent {

    public BookItemRequestCanceledEvent(BookItemId bookItemId, UserId userId, Title bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
