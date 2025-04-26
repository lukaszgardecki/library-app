package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.NotificationId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationAccessControlService {
    private final NotificationService notificationService;
//    private final AuthenticationFacade authFacade;

    void validateAccess(NotificationId notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
//        authFacade.validateOwnerOrAdminAccess(notification.getUserId());
    }
}
