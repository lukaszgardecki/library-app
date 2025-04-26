package com.example.notificationservice.domain.ports;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.NotificationId;
import com.example.notificationservice.domain.model.UserId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {
    Page<Notification> findByUserId(UserId userId, Pageable pageable);

    Optional<Notification> findById(NotificationId id);

    Notification save(Notification notification);

    void markAsRead(NotificationId id);

    void deleteById(NotificationId id);

    void deleteByIds(List<NotificationId> ids);
}
