package com.example.notificationservice.core;

import com.example.notificationservice.domain.dto.NotificationDto;
import com.example.notificationservice.domain.model.*;
import com.example.notificationservice.domain.model.values.*;

class NotificationMapper {

    static Notification toModel(NotificationDto dto) {
        return Notification.builder()
                .id(new NotificationId(dto.getId()))
                .type(dto.getType())
                .createdAt(new NotificationCreationDate(dto.getCreatedAt()))
                .subject(new NotificationSubject(dto.getSubject()))
                .content(new NotificationContent(dto.getContent()))
                .userId(new UserId(dto.getUserId()))
                .isRead(new IsRead(dto.getIsRead()))
                .build();
    }

    static NotificationDto toDto(Notification model) {
        return NotificationDto.builder()
                .id(model.getId().value())
                .type(model.getType())
                .createdAt(model.getCreatedAt().value())
                .subject(model.getSubject().value())
                .content(model.getContent().value())
                .userId(model.getUserId().value())
                .isRead(model.getIsRead().value())
                .build();
    }
}
