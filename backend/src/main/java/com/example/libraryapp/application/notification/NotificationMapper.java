package com.example.libraryapp.application.notification;

import com.example.libraryapp.domain.notification.dto.NotificationDto;
import com.example.libraryapp.domain.notification.model.Notification;

class NotificationMapper {

    static Notification toModel(NotificationDto dto) {
        return Notification.builder()
                .id(dto.getId())
                .type(dto.getType())
                .createdAt(dto.getCreatedAt())
                .subject(dto.getSubject())
                .content(dto.getContent())
                .userId(dto.getUserId())
                .isRead(dto.getIsRead())
                .build();
    }

    static NotificationDto toDto(Notification model) {
        return NotificationDto.builder()
                .id(model.getId())
                .type(model.getType())
                .createdAt(model.getCreatedAt())
                .subject(model.getSubject())
                .content(model.getContent())
                .userId(model.getUserId())
                .isRead(model.getIsRead())
                .build();
    }
}
