package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitem.Price;
import com.example.userservice.domain.model.book.Title;
import com.example.userservice.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class BookItemLostEvent extends BookItemEvent {
    private final Price charge;

    public BookItemLostEvent(BookItemId bookItemId, UserId userId, Title bookTitle, Price charge) {
        super(bookItemId, userId, bookTitle);
        this.charge = charge;
    }
}
