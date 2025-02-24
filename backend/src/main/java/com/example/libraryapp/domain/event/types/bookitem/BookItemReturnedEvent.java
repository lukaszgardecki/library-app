package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookItemReturnedEvent extends BookItemEvent {
    private final LocalDateTime dueDate;
    public BookItemReturnedEvent(Long bookItemId, Long userId, String bookTitle, LocalDateTime dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
