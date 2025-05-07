package com.example.notificationservice.infrastructure.persistence.jpa.notification;

import com.example.notificationservice.domain.model.*;
import com.example.notificationservice.domain.model.values.*;
import com.example.notificationservice.domain.ports.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class NotificationRepositoryPortAdapter implements NotificationRepositoryPort {
    private final JpaNotificationRepository repository;

    @Override
    public Page<Notification> findByUserId(UserId userId, Pageable pageable) {
        return repository.findAllByParams(userId.value(), pageable).map(this::toModel);
    }

    @Override
    public Optional<Notification> findById(NotificationId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Notification save(Notification notification) {
        NotificationEntity savedNotification = repository.save(toEntity(notification));
        return toModel(savedNotification);
    }

    @Override
    @Transactional
    public void markAsRead(NotificationId id) {
        repository.markAsRead(id.value());
    }

    @Override
    @Transactional
    public void deleteById(NotificationId id) {
        repository.deleteById(id.value());
    }

    @Override
    @Transactional
    public void deleteByIds(List<NotificationId> ids) {
        repository.deleteAllById(ids.stream().map(NotificationId::value).toList());
    }

    private NotificationEntity toEntity(Notification model) {
        return NotificationEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .createdAt(model.getCreatedAt().value())
                .subject(model.getSubject().value())
                .content(model.getContent().value())
                .type(model.getType())
                .isRead(model.getIsRead().value())
                .userId(model.getUserId().value())
                .build();
    }

    private Notification toModel(NotificationEntity entity) {
        return Notification.builder()
                .id(new NotificationId(entity.getId()))
                .createdAt(new NotificationCreationDate(entity.getCreatedAt()))
                .subject(new NotificationSubject(entity.getSubject()))
                .content(new NotificationContent(entity.getContent()))
                .type(entity.getType())
                .isRead(new IsRead(entity.getIsRead()))
                .userId(new UserId(entity.getUserId()))
                .build();
    }


}
