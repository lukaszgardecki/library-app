package com.example.activityservice.domain.ports.in;

import com.example.activityservice.domain.model.Activity;

public interface ActivityCreationStrategyPort<T> {
    Activity create(T event);
    Class<T> supports();
}
