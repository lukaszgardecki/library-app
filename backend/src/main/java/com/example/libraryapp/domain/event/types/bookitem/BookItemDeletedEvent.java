package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

@Getter
public class BookItemDeletedEvent extends BookItemEvent {

    public BookItemDeletedEvent(Long bookItemId, String bookTitle) {
        super(bookItemId, -1L, bookTitle);
    }
}
