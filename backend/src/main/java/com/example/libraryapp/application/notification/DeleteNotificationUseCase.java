package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class DeleteNotificationUseCase {
    private final NotificationAccessControlService notificationAccessControlService;
    private final NotificationRepositoryPort notificationRepository;

    void execute(Long notificationId) {
        notificationAccessControlService.validateAccess(notificationId);
        notificationRepository.deleteById(notificationId);
    }

    void execute(List<Long> ids) {
        ids.stream()
            .peek(notificationAccessControlService::validateAccess)
            .forEach(notificationRepository::deleteById);
    }
}
