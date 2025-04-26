package com.example.notificationservice.infrastructure.persistence.jpa.notification;

import com.example.notificationservice.domain.model.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private String subject;
    private String content;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private Boolean isRead;
    private Long userId;
}
