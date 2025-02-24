package com.example.libraryapp.domain.event.types.bookitem;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class BookItemReservedEvent extends BookItemEvent {
    private final int queue;
    private final LocalDate dueDate;

    public BookItemReservedEvent(
            Long bookItemId, Long userId, String bookTitle, int queue, LocalDate dueDate
    ) {
        super(bookItemId, userId, bookTitle);
        this.queue = queue;
        this.dueDate = dueDate;
    }
}
