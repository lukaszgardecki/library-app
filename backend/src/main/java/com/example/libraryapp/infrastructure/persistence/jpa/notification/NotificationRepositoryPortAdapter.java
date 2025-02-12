package com.example.libraryapp.infrastructure.persistence.jpa.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
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
    public Page<Notification> findByUserId(Long userId, Pageable pageable) {
        return repository.findAllByParams(userId, pageable).map(this::toModel);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    @Transactional
    public void markAsRead(Long id) {
        repository.markAsRead(id);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        repository.deleteAllById(ids);
    }

    private NotificationEntity toEntity(Notification model) {
        return NotificationEntity.builder()
                .id(model.getId())
                .createdAt(model.getCreatedAt())
                .subject(model.getSubject())
                .content(model.getContent())
                .type(model.getType())
                .isRead(model.getIsRead())
                .userId(model.getUserId())
                .build();
    }

    private Notification toModel(NotificationEntity entity) {
        return Notification.builder()
                .id(entity.getId())
                .createdAt(entity.getCreatedAt())
                .subject(entity.getSubject())
                .content(entity.getContent())
                .type(entity.getType())
                .isRead(entity.getIsRead())
                .userId(entity.getUserId())
                .build();
    }


}
