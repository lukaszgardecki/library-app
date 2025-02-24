package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class BookItemLostEvent extends BookItemEvent {
    private final BigDecimal charge;

    public BookItemLostEvent(Long bookItemId, Long userId, String bookTitle, BigDecimal charge) {
        super(bookItemId, userId, bookTitle);
        this.charge = charge;
    }
}
