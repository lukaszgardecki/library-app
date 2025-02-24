package com.example.libraryapp.domain.notification.types.bookitem;

import com.example.libraryapp.domain.notification.model.Notification;

public abstract class BookItemNotification extends Notification {
    private Long bookItemId;
    private String bookItemTitle;

    public BookItemNotification(Long userId, Long bookItemId, String bookItemTitle) {
        super(userId);
        this.bookItemId = bookItemId;
        this.bookItemTitle = bookItemTitle;
    }
}
