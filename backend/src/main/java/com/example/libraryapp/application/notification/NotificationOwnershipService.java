package com.example.libraryapp.application.notification;

import com.example.libraryapp.application.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationOwnershipService {
    private final AuthenticationFacade authenticationFacade;

    void validateOwner(Long userId) {
        authenticationFacade.validateOwnerOrAdminAccess(userId);
    }
}
