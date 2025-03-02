package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetNotificationUseCase {
    private final NotificationAccessControlService notificationAccessControlService;
    private final NotificationService notificationService;

    Notification execute(Long notificationId) {
        notificationAccessControlService.validateAccess(notificationId);
        return notificationService.getNotificationById(notificationId);
    }
}
