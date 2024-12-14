package com.example.libraryapp.NEWdomain.notification.ports;

import com.example.libraryapp.NEWdomain.notification.model.NotificationPreferences;

import java.util.Optional;

public interface NotificationPreferencesRepositoryPort {
    Optional<NotificationPreferences> findByUserId(Long userId);
}
