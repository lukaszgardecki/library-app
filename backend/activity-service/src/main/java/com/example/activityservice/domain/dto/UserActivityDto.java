package com.example.activityservice.domain.dto;

import com.example.activityservice.domain.model.values.ActivityType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserActivityDto {
    private Long id;
    private Long userId;
    private ActivityType type;
    private String message;
    private LocalDateTime createdAt;
}
