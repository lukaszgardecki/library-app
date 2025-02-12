package com.example.libraryapp.domain.event.types.notification;

public class SmsNotificationSentEvent extends NotificationSentEvent {

    public SmsNotificationSentEvent(Long userId, String notificationSubject) {
        super(userId, notificationSubject);
    }
}


