package com.example.notificationservice.domain.ports.out;

import com.example.notificationservice.domain.model.NotificationPreferences;
import com.example.notificationservice.domain.model.values.UserId;

import java.util.Optional;

public interface NotificationPreferencesRepositoryPort {
    Optional<NotificationPreferences> findByUserId(UserId userId);
}
