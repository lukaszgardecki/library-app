package com.example.libraryapp.domain.notification.types;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class EmailNotification extends Notification {
    protected String userEmail;

    @Override
    public void send() {
        System.out.printf("""
                Email notification:
                Created at: %s
                Book: %s
                Content: %s
                """,
                createdAt, bookTitle, content);
    }
}
