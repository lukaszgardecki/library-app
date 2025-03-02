package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class MarkAsReadUseCase {
    private final NotificationAccessControlService notificationAccessControlService;
    private final NotificationRepositoryPort notificationRepository;

    void execute(Long notificationId) {
        notificationAccessControlService.validateAccess(notificationId);
        notificationRepository.markAsRead(notificationId);
    }
}
