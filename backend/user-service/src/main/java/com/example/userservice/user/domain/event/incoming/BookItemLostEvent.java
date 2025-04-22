package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.user.domain.model.bookitem.BookItemId;
import com.example.userservice.user.domain.model.bookitem.Price;
import com.example.userservice.user.domain.model.book.Title;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class BookItemLostEvent extends BookItemEvent {
    private final Price charge;

    public BookItemLostEvent(BookItemId bookItemId, UserId userId, Title bookTitle, Price charge) {
        super(bookItemId, userId, bookTitle);
        this.charge = charge;
    }
}
