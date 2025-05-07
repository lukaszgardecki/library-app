package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.values.UserId;
import com.example.notificationservice.domain.ports.out.NotificationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
class GetPageOfNotificationsByUserIdUseCase {
    private final NotificationAccessControlService notificationAccessControlService;
    private final NotificationRepositoryPort notificationRepository;

    Page<Notification> execute(UserId userId, Pageable pageable) {
        return notificationRepository.findByUserId(userId, pageable);
    }
}
