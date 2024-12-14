package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWapplication.auth.AuthenticationFacade;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class NotificationOwnershipService {
    private final AuthenticationFacade authenticationFacade;

    void validateOwner(Long userId) {
        authenticationFacade.validateOwnerOrAdminAccess(userId);
    }
}
