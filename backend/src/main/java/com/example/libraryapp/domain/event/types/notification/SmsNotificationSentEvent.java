package com.example.libraryapp.domain.event.types.notification;

import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.user.model.UserId;

public class SmsNotificationSentEvent extends NotificationSentEvent {

    public SmsNotificationSentEvent(UserId userId, NotificationSubject notificationSubject) {
        super(userId, notificationSubject);
    }
}


