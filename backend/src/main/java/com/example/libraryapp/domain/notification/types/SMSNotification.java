package com.example.libraryapp.domain.notification.types;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class SMSNotification extends Notification {
    protected String phoneNumber;

    @Override
    public void send() {
        System.out.printf("""
                SMS notification:
                Created at: %s
                Book: %s
                Content: %s
                """,
                createdAt, bookTitle, content);
    }
}