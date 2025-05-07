package com.example.notificationservice.infrastructure.persistence.jpa.notificationpreferences;

import com.example.notificationservice.domain.model.NotificationPreferences;
import com.example.notificationservice.domain.model.values.UserId;
import com.example.notificationservice.domain.ports.out.NotificationPreferencesRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class NotificationPreferencesRepositoryAdapter implements NotificationPreferencesRepositoryPort {
    private final JpaNotificationPreferencesRepository repository;

    @Override
    public Optional<NotificationPreferences> findByUserId(UserId userId) {
        return repository.findByUserId(userId.value()).map(this::toModel);
    }

    private NotificationPreferencesEntity toEntity(NotificationPreferences model) {
        return NotificationPreferencesEntity.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .emailEnabled(model.getEmailEnabled())
                .smsEnabled(model.getSmsEnabled())
                .build();
    }

    private NotificationPreferences toModel(NotificationPreferencesEntity entity) {
        return NotificationPreferences.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .emailEnabled(entity.getEmailEnabled())
                .smsEnabled(entity.getSmsEnabled())
                .build();
    }
}
