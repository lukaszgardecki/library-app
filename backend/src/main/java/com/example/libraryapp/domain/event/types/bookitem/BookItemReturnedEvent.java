package com.example.libraryapp.domain.event.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.bookitemloan.model.LoanReturnDate;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BookItemReturnedEvent extends BookItemEvent {
    private final LoanReturnDate dueDate;
    public BookItemReturnedEvent(BookItemId bookItemId, UserId userId, Title bookTitle, LoanReturnDate dueDate) {
        super(bookItemId, userId, bookTitle);
        this.dueDate = dueDate;
    }
}
