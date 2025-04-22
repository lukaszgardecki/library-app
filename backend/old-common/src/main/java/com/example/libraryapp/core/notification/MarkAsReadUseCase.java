package com.example.libraryapp.core.notification;

import com.example.libraryapp.domain.notification.model.NotificationId;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class MarkAsReadUseCase {
    private final NotificationAccessControlService notificationAccessControlService;
    private final NotificationRepositoryPort notificationRepository;

    void execute(NotificationId notificationId) {
        notificationAccessControlService.validateAccess(notificationId);
        notificationRepository.markAsRead(notificationId);
    }
}
