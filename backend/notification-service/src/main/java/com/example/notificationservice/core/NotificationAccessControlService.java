package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.NotificationId;
import com.example.notificationservice.domain.ports.SourceValidator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationAccessControlService {
    private final NotificationService notificationService;
    private final SourceValidator sourceValidator;

    void validateAccess(NotificationId notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        sourceValidator.validateUserIsOwner(notification.getUserId());
    }
}
