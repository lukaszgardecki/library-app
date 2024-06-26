package com.example.libraryapp.domain.notification;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification")
@NoArgsConstructor
@Getter
@Setter
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime createdAt;
    private String subject;
    private String content;
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    private Boolean read;

    private Long bookId;
    private String bookTitle;

    private Long memberId;

    public Notification(NotificationType type) {
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.read = false;
    }
}
