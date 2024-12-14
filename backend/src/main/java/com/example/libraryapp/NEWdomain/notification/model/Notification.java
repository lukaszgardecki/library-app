package com.example.libraryapp.NEWdomain.notification.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public abstract class Notification {
    protected Long id;
    protected Long userId;
    protected LocalDateTime createdAt;
    protected NotificationType type;

    protected String subject;
    protected String content;
    protected Boolean isRead;

    protected Notification(Long userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
        this.isRead = false;
    }
}
