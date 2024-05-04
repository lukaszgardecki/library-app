package com.example.libraryapp.domain.notification.types;

import lombok.experimental.SuperBuilder;


@SuperBuilder
public abstract class Notification {
    protected String createdAt;
    protected String content;
    protected String bookTitle;

    abstract void send();
}
