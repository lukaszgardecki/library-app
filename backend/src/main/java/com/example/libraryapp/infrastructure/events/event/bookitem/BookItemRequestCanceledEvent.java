package com.example.libraryapp.infrastructure.events.event.bookitem;

import lombok.Getter;

@Getter
public class BookItemRequestCanceledEvent extends BookItemEvent {

    public BookItemRequestCanceledEvent(Long bookItemId, Long userId, String bookTitle) {
        super(bookItemId, userId, bookTitle);
    }
}
