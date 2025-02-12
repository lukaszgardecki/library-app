package com.example.libraryapp.infrastructure.events.event.notification;

import lombok.Getter;

@Getter
public class EmailNotificationSentEvent extends NotificationSentEvent {

    public EmailNotificationSentEvent(Long userId, String notificationSubject) {
        super(userId, notificationSubject);
    }
}


