package com.example.libraryapp.domain.event.types.notification;

import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class EmailNotificationSentEvent extends NotificationSentEvent {

    public EmailNotificationSentEvent(UserId userId, NotificationSubject notificationSubject) {
        super(userId, notificationSubject);
    }
}


