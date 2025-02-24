package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfNotificationsByUserIdUseCase {
    private final NotificationOwnershipService notificationOwnershipService;
    private final NotificationRepositoryPort notificationRepository;

    Page<Notification> execute(Long userId, Pageable pageable) {
        notificationOwnershipService.validateOwner(userId);
        return notificationRepository.findByUserId(userId, pageable);
    }
}
