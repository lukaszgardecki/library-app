package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.user.domain.model.bookitem.BookItemId;
import com.example.userservice.user.domain.model.bookitemloan.LoanReturnDate;
import com.example.userservice.user.domain.model.book.Title;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class BookItemReturnedEvent extends BookItemEvent {
    private final LoanReturnDate dueDate;
    public BookItemReturnedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanReturnDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
