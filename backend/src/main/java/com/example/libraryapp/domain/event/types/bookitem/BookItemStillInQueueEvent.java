package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

@Getter
public class BookItemStillInQueueEvent extends BookItemEvent {
    private final int queue;

    public BookItemStillInQueueEvent(Long bookItemId, Long userId, String bookTitle, int queue) {
        super(bookItemId, userId, bookTitle);
        this.queue = queue;
    }
}
