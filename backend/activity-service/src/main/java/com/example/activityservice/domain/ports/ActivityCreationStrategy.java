package com.example.activityservice.domain.ports;

import com.example.activityservice.domain.model.Activity;

public interface ActivityCreationStrategy<T> {
    Activity create(T event);
    Class<T> supports();
}
