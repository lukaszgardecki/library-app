package com.example.activityservice.infrastructure.persistence.jpa;

import com.example.activityservice.domain.model.ActivityType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "activity")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
class ActivityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;

    @Enumerated(EnumType.STRING)
    private ActivityType type;

    @Size(max = 1500)
    private String message;
    private LocalDateTime createdAt;
}
