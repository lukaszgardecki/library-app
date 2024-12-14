package com.example.libraryapp.NEWinfrastructure.events.event.notification;

public class SystemNotificationSentEvent extends NotificationSentEvent {

    public SystemNotificationSentEvent(Long userId, String notificationSubject) {
        super(userId, notificationSubject);
    }
}



