package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitem.model.Price;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BookItemLostEvent extends BookItemEvent {
    private final Price charge;

    public BookItemLostEvent(BookItemId bookItemId, UserId userId, Title bookTitle, Price charge) {
        super(bookItemId, userId, bookTitle);
        this.charge = charge;
    }
}
