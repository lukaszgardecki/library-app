package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.ActivityCreationStrategy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityFactory {
    private final Map<Class<?>, ActivityCreationStrategy<?>> registry = new HashMap<>();

    public ActivityFactory(List<ActivityCreationStrategy<?>> strategies) {
        for (ActivityCreationStrategy<?> strategy : strategies) {
            register(strategy);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Activity createActivity(T event) {
        ActivityCreationStrategy<T> strategy = (ActivityCreationStrategy<T>) registry.get(event.getClass());
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for event type: " + event.getClass());
        }
        return strategy.create(event);
    }

    public <T> void register(ActivityCreationStrategy<T> strategy) {
        registry.put(strategy.supports(), strategy);
    }
}
