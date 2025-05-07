package com.example.activityservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Activity {
    private ActivityId id;
    private UserId userId;
    private ActivityType type;
    private ActivityMessage message;
    private ActivityCreationDate createdAt;

    public Activity(UserId userId, ActivityType type, ActivityMessage message) {
        this.userId = userId;
        this.type = type;
        this.message = message;
        this.createdAt = new ActivityCreationDate(LocalDateTime.now());
    }
}
