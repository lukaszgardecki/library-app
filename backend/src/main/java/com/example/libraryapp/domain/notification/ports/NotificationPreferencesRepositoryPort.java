package com.example.libraryapp.domain.notification.ports;

import com.example.libraryapp.domain.notification.model.NotificationPreferences;
import com.example.libraryapp.domain.user.model.UserId;

import java.util.Optional;

public interface NotificationPreferencesRepositoryPort {
    Optional<NotificationPreferences> findByUserId(UserId userId);
}
