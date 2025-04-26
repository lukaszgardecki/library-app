package com.example.notificationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Notification {
    private NotificationId id;
    private UserId userId;
    private NotificationCreationDate createdAt;
    private NotificationType type;
    private NotificationSubject subject;
    private NotificationContent content;
    private IsRead isRead;

    public Notification(NotificationSubject subject, NotificationContent content, NotificationType type, UserId userId) {
        this.userId = userId;
        this.createdAt = new NotificationCreationDate(LocalDateTime.now());
        this.type = type;
        this.subject = subject;
        this.content = content;
        this.isRead = new IsRead(false);
    }
}
