package com.example.libraryapp.application.notification;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.model.NotificationId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationAccessControlService {
    private final NotificationService notificationService;
    private final AuthenticationFacade authFacade;

    void validateAccess(NotificationId notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        authFacade.validateOwnerOrAdminAccess(notification.getUserId());
    }
}
