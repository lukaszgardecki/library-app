package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.NotificationId;
import com.example.notificationservice.domain.ports.NotificationRepositoryPort;
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
