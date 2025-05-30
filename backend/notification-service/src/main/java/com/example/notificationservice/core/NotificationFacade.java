package com.example.notificationservice.core;

import com.example.notificationservice.domain.model.Notification;
import com.example.notificationservice.domain.model.values.NotificationId;
import com.example.notificationservice.domain.model.values.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class NotificationFacade {
    private final GetPageOfNotificationsByUserIdUseCase getPageOfNotificationsByUserIdUseCase;
    private final GetNotificationUseCase getNotificationUseCase;
    private final MarkAsReadUseCase markAsReadUseCase;
    private final DeleteNotificationUseCase deleteNotificationUseCase;

    public Page<Notification> getPageOfNotificationsByUserId(UserId userId, Pageable pageable) {
        return getPageOfNotificationsByUserIdUseCase.execute(userId, pageable);
    }

    public Notification getNotification(NotificationId id) {
        return getNotificationUseCase.execute(id);
    }

    public void markAsRead(NotificationId id) {
        markAsReadUseCase.execute(id);
    }

    public void deleteNotification(NotificationId id) {
        deleteNotificationUseCase.execute(id);
    }

    public void deleteNotifications(List<NotificationId> ids) {
        deleteNotificationUseCase.execute(ids);
    }
}
