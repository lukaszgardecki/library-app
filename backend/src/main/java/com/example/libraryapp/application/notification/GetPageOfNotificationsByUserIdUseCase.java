package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.model.Notification;
import com.example.libraryapp.domain.notification.ports.NotificationRepositoryPort;
import com.example.libraryapp.domain.user.model.UserId;
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
