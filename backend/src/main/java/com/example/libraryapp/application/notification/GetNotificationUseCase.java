package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.exceptions.NotificationNotFoundException;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetNotificationUseCase {
    // TODO: 07.02.2025 czy ten serwis aby na pewno jest potrzebny?
    // TODO: 07.02.2025 czy nie lepiej wydizelić zwykły serwis z podpiętą AuthenticationFacade ?
    private final NotificationOwnershipService notificationOwnershipService;
    private final NotificationRepositoryPort notificationRepository;

    Notification execute(Long notificationId) {
        notificationOwnershipService.validateOwner(notificationId);
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException(notificationId));
    }
}
