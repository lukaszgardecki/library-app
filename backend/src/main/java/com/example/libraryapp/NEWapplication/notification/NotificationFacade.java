package com.example.libraryapp.NEWapplication.notification;

import com.example.libraryapp.NEWdomain.notification.dto.NotificationDto;
import com.example.libraryapp.NEWdomain.notification.model.Notification;
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

    public Page<NotificationDto> getPageOfNotificationsByUserId(Long userId, Pageable pageable) {
        return getPageOfNotificationsByUserIdUseCase.execute(userId, pageable)
                .map(NotificationMapper::toDto);
    }

    public NotificationDto getNotification(Long id) {
        Notification notification = getNotificationUseCase.execute(id);
        return NotificationMapper.toDto(notification);
    }

    public void markAsRead(Long id) {
        markAsReadUseCase.execute(id);
    }

    public void deleteNotification(Long id) {
        deleteNotificationUseCase.execute(id);
    }

    public void deleteNotifications(List<Long> ids) {
        deleteNotificationUseCase.execute(ids);
    }
}
