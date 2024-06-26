package com.example.libraryapp.web;

import com.example.libraryapp.domain.auth.AuthenticationService;
import com.example.libraryapp.domain.config.RoleAuthorization;
import com.example.libraryapp.domain.notification.NotificationService;
import com.example.libraryapp.domain.notification.dto.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.libraryapp.domain.member.Role.ADMIN;
import static com.example.libraryapp.domain.member.Role.USER;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;
    private final AuthenticationService authService;

    @GetMapping
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<PagedModel<NotificationDto>> getAllNotifications(
            @RequestParam(required = false) Long memberId,
            Pageable pageable
    ) {
        authService.checkIfAdminOrDataOwnerRequested(memberId);
        PagedModel<NotificationDto> collectionModel = notificationService.findNotifications(memberId, pageable);
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping("/{id}")
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<NotificationDto> getNotificationById(@PathVariable Long id) {
        NotificationDto notification = notificationService.findNotificationById(id);
        authService.checkIfAdminOrDataOwnerRequested(notification.getMemberId());
        return ResponseEntity.ok(notification);
    }

    @PostMapping("/{id}")
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<Void> markAsRead(@PathVariable Long id) {
        NotificationDto notification = notificationService.findNotificationById(id);
        authService.checkIfAdminOrDataOwnerRequested(notification.getMemberId());
        notificationService.markAsRead(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<Void> deleteNotification(@PathVariable Long id) {
        NotificationDto notification = notificationService.findNotificationById(id);
        authService.checkIfAdminOrDataOwnerRequested(notification.getMemberId());
        notificationService.deleteNotification(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @RoleAuthorization({ADMIN, USER})
    public ResponseEntity<Void> deleteNotifications(@RequestBody List<Long> ids) {
        Long userId = authService.getCurrentLoggedInUserId();
        boolean userIsAdmin = authService.checkIfCurrentLoggedInUserIsAdmin();
        notificationService.deleteNotifications(ids, userId, userIsAdmin);
        return ResponseEntity.noContent().build();
    }
}
