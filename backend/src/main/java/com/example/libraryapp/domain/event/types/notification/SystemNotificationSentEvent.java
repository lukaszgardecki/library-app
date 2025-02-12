package com.example.libraryapp.domain.event.types.notification;

public class SystemNotificationSentEvent extends NotificationSentEvent {

    public SystemNotificationSentEvent(Long userId, String notificationSubject) {
        super(userId, notificationSubject);
    }
}



