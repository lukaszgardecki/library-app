package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.values.NotificationId;
import com.example.notificationservice.domain.ports.out.NotificationRepositoryPort;
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
