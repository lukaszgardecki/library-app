package com.example.libraryapp.infrastructure.events.event.notification;

public class SmsNotificationSentEvent extends NotificationSentEvent {

    public SmsNotificationSentEvent(Long userId, String notificationSubject) {
        super(userId, notificationSubject);
    }
}


