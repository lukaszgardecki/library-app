package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.book.model.Title;
import com.example.libraryapp.domain.bookitem.model.BookItemId;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.user.model.UserId;

public abstract class BookItemNotification extends Notification {
    private BookItemId bookItemId;
    private Title bookItemTitle;

    public BookItemNotification(UserId userId, BookItemId bookItemId, Title bookItemTitle) {
        super(userId);
        this.bookItemId = bookItemId;
        this.bookItemTitle = bookItemTitle;
    }
}
