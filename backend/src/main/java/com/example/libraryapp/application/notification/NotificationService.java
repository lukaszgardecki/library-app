package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.exceptions.NotificationNotFoundException;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.model.NotificationId;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationService {
    private final NotificationRepositoryPort repositoryPort;

    Notification getNotificationById(NotificationId id) {
        return repositoryPort.findById(id)
                .orElseThrow(() -> new NotificationNotFoundException(id));
    }
}
