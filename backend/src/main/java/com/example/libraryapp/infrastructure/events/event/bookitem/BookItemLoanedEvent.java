package com.example.libraryapp.infrastructure.events.event.bookitem;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookItemLoanedEvent extends BookItemEvent {
    private final LocalDateTime creationDate;
    private final LocalDateTime dueDate;
    private final boolean isReferenceOnly;
    
    public BookItemLoanedEvent(Long bookItemId, Long userId, String bookTitle, boolean isReferenceOnly, LocalDateTime creationDate, LocalDateTime dueDate) {
        super(bookItemId, userId, bookTitle);
        this.creationDate = creationDate;
        this.dueDate = dueDate;
        this.isReferenceOnly = isReferenceOnly;
    }
}

