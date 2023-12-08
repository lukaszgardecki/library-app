package com.example.libraryapp.domain.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public static final String RESERVATION_CREATED = "The reservation was successfully created.";
    public static final String RESERVATION_DELETED = "The reservation was successfully deleted.";
    public static final String BOOK_BORROWED = "The book has been borrowed.";
    public static final String BOOK_EXTENDED = "The book has been extended";

    public void send(String notificationMessage) {
        System.out.println(notificationMessage);
    }
}
