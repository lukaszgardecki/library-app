package com.example.libraryapp.domain.notification.model;

import com.example.libraryapp.domain.user.model.UserId;
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
    protected NotificationId id;
    protected UserId userId;
    protected NotificationCreationDate createdAt;
    protected NotificationType type;
    protected NotificationSubject subject;
    protected NotificationContent content;
    protected IsRead isRead;

    protected Notification(UserId userId) {
        this.userId = userId;
        this.createdAt = new NotificationCreationDate(LocalDateTime.now());
        this.isRead = new IsRead(false);
    }
}
