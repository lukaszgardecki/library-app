package com.example.libraryapp.domain.notification;

import com.example.libraryapp.domain.notification.types.EmailNotification;
import com.example.libraryapp.domain.notification.types.SMSNotification;

import java.time.format.DateTimeFormatter;

public class NotificationFactory {

    public static EmailNotification createEmailNotification(NotificationDetails details) {
        return EmailNotification.builder()
                .userEmail(details.getMemberEmail())
                .createdAt(details.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")))
                .bookTitle(details.getBookTitle())
                .content(details.getContent())
                .build();
    }

    public static SMSNotification createSMSNotification(NotificationDetails details) {
        return SMSNotification.builder()
                .phoneNumber(details.getMemberPhoneNumber())
                .createdAt(details.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss")))
                .bookTitle(details.getBookTitle())
                .content(details.getContent())
                .build();
    }
}
