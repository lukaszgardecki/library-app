package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.model.bookitem.BookItemId;
import com.example.userservice.domain.model.bookitemloan.LoanReturnDate;
import com.example.userservice.domain.model.book.Title;
import lombok.Getter;

@Getter
public class BookItemReturnedEvent extends BookItemEvent {
    private final LoanReturnDate dueDate;
    public BookItemReturnedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanReturnDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
