package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class BookItemReservedEvent extends BookItemEvent {
    private final int queue;
    private final LoanDueDate dueDate;

    public BookItemReservedEvent(
            BookItemId bookItemId, UserId userId, Title bookTitle, int queue, LoanDueDate dueDate
    ) {
        super(bookItemId, userId, bookTitle);
        this.queue = queue;
        this.dueDate = dueDate;
    }
}
