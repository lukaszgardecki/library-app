package com.example.libraryapp.domain.event.types.notification;

import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.user.model.UserId;

public class SystemNotificationSentEvent extends NotificationSentEvent {

    public SystemNotificationSentEvent(UserId userId, NotificationSubject notificationSubject) {
        super(userId, notificationSubject);
    }
}



