package com.example.libraryapp.NEWdomain.notification.dto;

import com.example.libraryapp.NEWdomain.notification.model.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private LocalDateTime createdAt;
    private String subject;
    private String content;
    private Long bookId;
    private String bookTitle;
    private Long userId;
    private NotificationType type;
    private Boolean isRead;
}
