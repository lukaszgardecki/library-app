package com.example.libraryapp.NEWinfrastructure.persistence.jpa.notification;

import com.example.libraryapp.NEWdomain.notification.model.NotificationType;
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
    private NotificationType type;
    private Boolean isRead;
    private Long bookId;
    private String bookTitle;
    private Long userId;
}
