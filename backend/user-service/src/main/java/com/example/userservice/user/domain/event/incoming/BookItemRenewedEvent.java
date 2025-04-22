package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.user.domain.model.bookitem.BookItemId;
import com.example.userservice.user.domain.model.bookitemloan.LoanDueDate;
import com.example.userservice.user.domain.model.book.Title;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class BookItemRenewedEvent extends BookItemEvent {
    private final LoanDueDate dueDate;

    public BookItemRenewedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanDueDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
