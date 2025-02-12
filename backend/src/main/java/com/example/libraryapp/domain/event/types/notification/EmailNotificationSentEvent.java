package com.example.libraryapp.domain.event.types.notification;

import lombok.Getter;

@Getter
public class EmailNotificationSentEvent extends NotificationSentEvent {

    public EmailNotificationSentEvent(Long userId, String notificationSubject) {
        super(userId, notificationSubject);
    }
}


