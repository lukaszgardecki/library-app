package com.example.notificationservice.core;

import com.example.notificationservice.domain.exceptions.NotificationNotFoundException;
import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.values.NotificationId;
import com.example.notificationservice.domain.ports.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationService {
    private final NotificationRepositoryPort repositoryPort;

    Notification getNotificationById(NotificationId id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }
}
