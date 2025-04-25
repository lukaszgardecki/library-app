package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRequestCanceledEvent extends BookItemEvent {

    public BookItemRequestCanceledEvent(BookItemId bookItemId, UserId userId, Title bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
