package com.example.libraryapp.domain.notification.dto;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto extends RepresentationModel<NotificationDto> {
    private Long id;
    private LocalDateTime createdAt;
    private String subject;
    private String content;
    private Long bookId;
    private String bookTitle;
    private Long memberId;
    private String type;
    private Boolean read;
}
