package com.example.catalogservice.domain.event.incoming;

import com.example.catalogservice.domain.event.outgoing.BookItemEvent;
import com.example.catalogservice.domain.model.book.Title;
import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.Price;
import com.example.catalogservice.domain.model.UserId;
import lombok.Getter;

@Getter
public class BookItemLostEvent extends BookItemEvent {
    private final Price charge;

    public BookItemLostEvent(BookItemId bookItemId, UserId userId, Title bookTitle, Price charge) {
        super(bookItemId, userId, bookTitle);
        this.charge = charge;
    }
}
