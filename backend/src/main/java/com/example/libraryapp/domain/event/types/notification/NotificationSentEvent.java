package com.example.libraryapp.domain.event.types.notification;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.notification.model.NotificationSubject;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public abstract class NotificationSentEvent extends CustomEvent {
    private final NotificationSubject notificationSubject;

    public NotificationSentEvent(UserId userId, NotificationSubject notificationSubject) {
        super(userId);
        this.notificationSubject = notificationSubject;
    }
}
