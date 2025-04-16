package com.example.libraryapp.domain.notification.ports;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.model.NotificationId;
import com.example.libraryapp.domain.user.model.UserId;
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
