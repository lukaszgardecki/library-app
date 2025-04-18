package com.example.libraryapp.adapter.http;

import com.example.libraryapp.application.notification.NotificationFacade;
import com.example.libraryapp.domain.notification.dto.NotificationDto;
import com.example.libraryapp.domain.notification.model.NotificationId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
@RequiredArgsConstructor
class NotificationController {
    private final NotificationFacade notificationFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == authentication.principal.id")
    public ResponseEntity<Page<NotificationDto>> getAllNotifications(
            @RequestParam(required = false) Long userId,
            Pageable pageable
    ) {
        Page<NotificationDto> page = notificationFacade.getPageOfNotificationsByUserId(new UserId(userId), pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        NotificationDto notification = notificationFacade.getNotification(new NotificationId(id));
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationFacade.markAsRead(new NotificationId(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationFacade.deleteNotification(new NotificationId(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNotifications(@RequestBody List<Long> ids) {
        notificationFacade.deleteNotifications(ids.stream().map(NotificationId::new).toList());
        return ResponseEntity.noContent().build();
    }
}
