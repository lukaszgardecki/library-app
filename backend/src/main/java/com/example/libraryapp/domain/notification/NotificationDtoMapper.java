package com.example.libraryapp.domain.notification;

import com.example.libraryapp.domain.notification.dto.NotificationDto;

public class NotificationDtoMapper {

    public static NotificationDto map(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .type(notification.getType())
                .createdAt(notification.getCreatedAt())
                .subject(notification.getSubject())
                .content(notification.getContent())
                .memberId(notification.getMemberId())
                .read(notification.getRead())
                .bookId(notification.getBookId())
                .bookTitle(notification.getBookTitle())
                .build();
    }
}
