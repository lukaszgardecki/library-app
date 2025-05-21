package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.values.NotificationId;
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
