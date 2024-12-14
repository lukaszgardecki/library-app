package com.example.libraryapp.NEWinfrastructure.events.event.notification;

import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import lombok.Getter;

@Getter
public abstract class NotificationSentEvent extends CustomEvent {
    private String notificationSubject;

    public NotificationSentEvent(Long userId, String notificationSubject) {
        super(userId);
        this.notificationSubject = notificationSubject;
    }
}
