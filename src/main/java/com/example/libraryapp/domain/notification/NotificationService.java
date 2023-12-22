package com.example.libraryapp.domain.notification;

import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    public static final String RESERVATION_CREATED = "The reservation has been successfully created.";
    public static final String RESERVATION_DELETED = "The reservation has been successfully deleted.";
    public static final String BOOK_BORROWED = "The book has been successfully borrowed.";
    public static final String BOOK_EXTENDED = "The book has been successfully extended";
    public static final String BOOK_RETURNED = "The book has been successfully returned";

    public void send(String notificationMessage) {
        System.out.println(notificationMessage);
    }
}
