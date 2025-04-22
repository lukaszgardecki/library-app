package com.example.libraryapp.core.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.model.NotificationId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetNotificationUseCase {
    private final NotificationAccessControlService notificationAccessControlService;
    private final NotificationService notificationService;

    Notification execute(NotificationId notificationId) {
        notificationAccessControlService.validateAccess(notificationId);
        return notificationService.getNotificationById(notificationId);
    }
}
