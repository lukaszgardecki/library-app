package com.example.libraryapp.core.notification;

import com.example.libraryapp.domain.notification.model.NotificationId;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class DeleteNotificationUseCase {
    private final NotificationAccessControlService notificationAccessControlService;
    private final NotificationRepositoryPort notificationRepository;

    void execute(NotificationId notificationId) {
        notificationAccessControlService.validateAccess(notificationId);
        notificationRepository.deleteById(notificationId);
    }

    void execute(List<NotificationId> ids) {
        ids.stream()
            .peek(notificationAccessControlService::validateAccess)
            .forEach(notificationRepository::deleteById);
    }
}
