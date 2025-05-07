package com.example.notificationservice.domain.dto;

import com.example.notificationservice.domain.model.values.NotificationType;
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
