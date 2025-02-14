package com.example.libraryapp.domain.notification.ports;

import com.example.libraryapp.domain.notification.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface NotificationRepositoryPort {
    Page<Notification> findByUserId(Long userId, Pageable pageable);

    Optional<Notification> findById(Long id);

    Notification save(Notification notification);

    void markAsRead(Long id);

    void deleteById(Long id);

    void deleteByIds(List<Long> ids);
}
