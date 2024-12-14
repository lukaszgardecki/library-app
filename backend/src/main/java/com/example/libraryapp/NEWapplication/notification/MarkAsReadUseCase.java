package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWdomain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class MarkAsReadUseCase {
    private final NotificationOwnershipService notificationOwnershipService;
    private final NotificationRepositoryPort notificationRepository;

    void execute(Long notificationId) {
        notificationOwnershipService.validateOwner(notificationId);
        notificationRepository.markAsRead(notificationId);
    }
}
