package com.example.util;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.model.values.*;

import java.time.LocalDateTime;

public class ActivityTestFactory {

    public static Activity defaultActivity() {
        return new Activity(
                new UserId(1L),
                ActivityType.LOGIN,
                new ActivityMessage("testMessage")
        );
    }

    public static Activity activityWith(ActivityId id) {
        return new Activity(
                id,
                null,
                ActivityType.LOGIN,
                new ActivityMessage("test"),
                new ActivityCreationDate(LocalDateTime.of(2025, 5, 30, 8, 0))
        );
    }

    public static Activity activityWith(UserId userId) {
        return new Activity(
                null,
                userId,
                ActivityType.LOGIN,
                new ActivityMessage("test"),
                new ActivityCreationDate(LocalDateTime.of(2025, 5, 30, 8, 0))
        );
    }

    public static Activity activityWith(ActivityId id, UserId userId) {
        return new Activity(
                id,
                userId,
                ActivityType.LOGIN,
                new ActivityMessage("test"),
                new ActivityCreationDate(LocalDateTime.of(2025, 5, 30, 8, 0))
        );
    }
}
