package com.example.libraryapp.core.notification;

import com.example.libraryapp.core.auth.AuthenticationFacade;
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
