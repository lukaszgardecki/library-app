package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.in.ActivityCreationStrategyPort;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityFactory {
    private final Map<Class<?>, ActivityCreationStrategyPort<?>> registry = new HashMap<>();

    public ActivityFactory(List<ActivityCreationStrategyPort<?>> strategies) {
        for (ActivityCreationStrategyPort<?> strategy : strategies) {
            register(strategy);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> Activity createActivity(T event) {
        ActivityCreationStrategyPort<T> strategy = (ActivityCreationStrategyPort<T>) registry.get(event.getClass());
        if (strategy == null) {
            throw new IllegalArgumentException("No strategy found for event type: " + event.getClass());
        }
        return strategy.create(event);
    }

    public <T> void register(ActivityCreationStrategyPort<T> strategy) {
        registry.put(strategy.supports(), strategy);
    }
}
