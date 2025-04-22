package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class BookItemRenewedEvent extends BookItemEvent {
    private final LoanDueDate dueDate;

    public BookItemRenewedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanDueDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
