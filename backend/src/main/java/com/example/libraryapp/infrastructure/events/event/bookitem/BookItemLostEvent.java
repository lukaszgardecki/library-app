package com.example.libraryapp.infrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemLostEvent extends BookItemEvent {

    public BookItemLostEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
