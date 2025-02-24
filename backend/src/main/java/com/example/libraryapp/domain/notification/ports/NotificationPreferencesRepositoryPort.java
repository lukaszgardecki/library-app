package com.example.libraryapp.domain.notification.ports;

import com.example.libraryapp.domain.notification.model.NotificationPreferences;

import java.util.Optional;

public interface NotificationPreferencesRepositoryPort {
    Optional<NotificationPreferences> findByUserId(Long userId);
}
