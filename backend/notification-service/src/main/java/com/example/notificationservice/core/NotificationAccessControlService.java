package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.values.NotificationId;
import com.example.notificationservice.domain.ports.out.SourceValidatorPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationAccessControlService {
    private final NotificationService notificationService;
    private final SourceValidatorPort sourceValidator;

    void validateAccess(NotificationId notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        sourceValidator.validateUserIsOwner(notification.getUserId());
    }
}
