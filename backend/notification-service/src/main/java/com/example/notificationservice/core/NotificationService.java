package com.example.notificationservice.core;

import com.example.notificationservice.domain.exceptions.NotificationNotFoundException;
import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.NotificationId;
import com.example.notificationservice.domain.ports.NotificationRepositoryPort;
import com.example.notificationservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationService {
    private final NotificationRepositoryPort repositoryPort;
    private final SourceValidator sourceValidator;

    Notification getNotificationById(NotificationId id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }
}
