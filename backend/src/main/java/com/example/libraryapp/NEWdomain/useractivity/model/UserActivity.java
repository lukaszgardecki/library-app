package com.example.libraryapp.NEWdomain.useractivity.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public abstract class UserActivity {
    protected Long id;
    protected Long userId;
    protected UserActivityType type;
    protected String message;
    protected LocalDateTime createdAt;

    protected UserActivity(Long userId) {
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
