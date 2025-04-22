package com.example.catalogservice.bookitem.domain.event.incoming;

import com.example.catalogservice.book.domain.model.Title;
import com.example.catalogservice.bookitem.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.Price;
import com.example.catalogservice.bookitem.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemLostEvent extends BookItemEvent {
    private final Price charge;

    public BookItemLostEvent(BookItemId bookItemId, UserId userId, Title bookTitle, Price charge) {
        super(bookItemId, userId, bookTitle);
        this.charge = charge;
    }
}
