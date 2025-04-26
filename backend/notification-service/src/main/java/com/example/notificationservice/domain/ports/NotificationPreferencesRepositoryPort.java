package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.model.NotificationPreferences;
import com.example.notificationservice.domain.model.UserId;

import java.util.Optional;

public interface NotificationPreferencesRepositoryPort {
    Optional<NotificationPreferences> findByUserId(UserId userId);
}
