package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitemloan.LoanDueDate;
import com.example.userservice.domain.model.book.Title;
import lombok.Getter;

@Getter
public class BookItemRenewedEvent extends BookItemEvent {
    private final LoanDueDate dueDate;

    public BookItemRenewedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanDueDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
