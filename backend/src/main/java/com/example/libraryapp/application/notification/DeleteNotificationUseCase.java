package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class DeleteNotificationUseCase {
    private final NotificationOwnershipService notificationOwnershipService;
    private final NotificationRepositoryPort notificationRepository;

    void execute(Long notificationId) {
        notificationOwnershipService.validateOwner(notificationId);
        notificationRepository.deleteById(notificationId);
    }

    void execute(List<Long> ids) {
        ids.stream()
                .peek(notificationOwnershipService::validateOwner)
                .forEach(notificationRepository::deleteById);
    }
}
