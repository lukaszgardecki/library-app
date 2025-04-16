package com.example.libraryapp.domain.useractivity.model;

import com.example.libraryapp.domain.user.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserActivity {
    protected UserActivityId id;
    protected UserId userId;
    protected UserActivityType type;
    protected UserActivityMessage message;
    protected UserActivityCreationDate createdAt;

    protected UserActivity(UserId userId) {
        this.userId = userId;
        this.createdAt = new UserActivityCreationDate(LocalDateTime.now());
    }
}
