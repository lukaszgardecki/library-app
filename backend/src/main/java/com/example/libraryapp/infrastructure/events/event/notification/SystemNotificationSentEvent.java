package com.example.libraryapp.infrastructure.events.event.notification;

public class SystemNotificationSentEvent extends NotificationSentEvent {

    public SystemNotificationSentEvent(Long userId, String notificationSubject) {
        super(userId, notificationSubject);
    }
}



