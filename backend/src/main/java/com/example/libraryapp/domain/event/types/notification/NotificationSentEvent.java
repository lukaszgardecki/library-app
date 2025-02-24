package com.example.libraryapp.domain.event.types.notification;

import com.example.libraryapp.domain.event.types.CustomEvent;
import lombok.Getter;

@Getter
public abstract class NotificationSentEvent extends CustomEvent {
    private final String notificationSubject;

    public NotificationSentEvent(Long userId, String notificationSubject) {
        super(userId);
        this.notificationSubject = notificationSubject;
    }
}
