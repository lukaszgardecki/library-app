package com.example.notificationservice.infrastructure.http;

import com.example.notificationservice.core.NotificationFacade;
import com.example.notificationservice.domain.model.values.NotificationId;
import com.example.notificationservice.domain.model.values.UserId;
import com.example.notificationservice.infrastructure.http.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
class NotificationController {
    private final NotificationFacade notificationFacade;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or #userId == principal")
    ResponseEntity<Page<NotificationDto>> getAllNotifications(
            @RequestParam(name = "user_id", required = false) Long userId,
            Pageable pageable
    ) {
        Page<NotificationDto> page = notificationFacade.getPageOfNotificationsByUserId(new UserId(userId), pageable)
                .map(NotificationMapper::toDto);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        NotificationDto notification = NotificationMapper.toDto(
                notificationFacade.getNotification(new NotificationId(id))
        );
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/{id}/read")
    ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationFacade.markAsRead(new NotificationId(id));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationFacade.deleteNotification(new NotificationId(id));
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    ResponseEntity<Void> deleteNotifications(@RequestBody List<Long> ids) {
        notificationFacade.deleteNotifications(ids.stream().map(NotificationId::new).toList());
        return ResponseEntity.noContent().build();
    }
}
