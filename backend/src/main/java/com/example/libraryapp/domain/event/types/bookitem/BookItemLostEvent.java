package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

@Getter
public class BookItemLostEvent extends BookItemEvent {

    public BookItemLostEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
