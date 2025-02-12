package com.example.libraryapp.infrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemRequestReadyEvent extends BookItemEvent {

    public BookItemRequestReadyEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
