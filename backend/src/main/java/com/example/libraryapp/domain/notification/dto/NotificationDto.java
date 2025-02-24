package com.example.libraryapp.domain.notification.dto;

import com.example.libraryapp.domain.notification.model.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private Long userId;
    private LocalDateTime createdAt;
    private NotificationType type;
    private String subject;
    private String content;
    private Boolean isRead;
}
