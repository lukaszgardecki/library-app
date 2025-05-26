package com.example.notificationservice.infrastructure.persistence.jpa.notificationpreferences;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notification_preferences")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class NotificationPreferencesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Boolean emailEnabled;
    private Boolean smsEnabled;
}
