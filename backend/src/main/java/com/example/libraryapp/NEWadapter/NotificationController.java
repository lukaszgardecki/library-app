package com.example.libraryapp.NEWadapter;

import com.example.libraryapp.NEWapplication.notification.NotificationFacade;
import com.example.libraryapp.NEWdomain.notification.dto.NotificationDto;
import com.example.libraryapp.NEWinfrastructure.security.RoleAuthorization;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.libraryapp.NEWdomain.user.model.Role.ADMIN;
import static com.example.libraryapp.NEWdomain.user.model.Role.USER;

@RestController
@RequestMapping("/api/v1/notifications")
@RoleAuthorization({ADMIN, USER})
@RequiredArgsConstructor
class NotificationController {
    private final NotificationFacade notificationFacade;

    @GetMapping
    public ResponseEntity<Page<NotificationDto>> getAllNotifications(
            @RequestParam(required = false) Long memberId,
            Pageable pageable
    ) {
        Page<NotificationDto> page = notificationFacade.getPageOfNotificationsByUserId(memberId, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        NotificationDto notification = notificationFacade.getNotification(id);
        return ResponseEntity.ok(notification);
    }

    @PatchMapping("/{id}/read")
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        notificationFacade.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        notificationFacade.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteNotifications(@RequestBody List<Long> ids) {
        notificationFacade.deleteNotifications(ids);
        return ResponseEntity.noContent().build();
    }
}
