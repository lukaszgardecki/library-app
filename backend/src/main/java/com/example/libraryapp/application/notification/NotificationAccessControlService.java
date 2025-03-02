package com.example.libraryapp.application.notification;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.notification.model.Notification;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationAccessControlService {
    private final NotificationService notificationService;
    private final AuthenticationFacade authFacade;

    void validateAccess(Long notificationId) {
        Notification notification = notificationService.getNotificationById(notificationId);
        authFacade.validateOwnerOrAdminAccess(notification.getUserId());
    }
}
