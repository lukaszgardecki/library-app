package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWdomain.notification.exceptions.NotificationNotFoundException;
import com.example.libraryapp.NEWdomain.notification.model.Notification;
import com.example.libraryapp.NEWdomain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetNotificationUseCase {
    private final NotificationOwnershipService notificationOwnershipService;
    private final NotificationRepositoryPort notificationRepository;

    Notification execute(Long notificationId) {
        notificationOwnershipService.validateOwner(notificationId);
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }
}
