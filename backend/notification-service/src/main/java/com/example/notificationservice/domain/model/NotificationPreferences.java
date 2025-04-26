package com.example.notificationservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public
class NotificationPreferences {
    private Long id;
    private Long userId;
    private Boolean emailEnabled;
    private Boolean smsEnabled;
}
