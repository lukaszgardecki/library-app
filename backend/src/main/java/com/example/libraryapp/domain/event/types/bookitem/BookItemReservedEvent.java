package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

@Getter
public class BookItemReservedEvent extends BookItemEvent {
    private final int queue;

    public BookItemReservedEvent(Long bookItemId, Long userId, String bookTitle, int queue) {
        super(bookItemId, userId, bookTitle);
        this.queue = queue;
    }
}
