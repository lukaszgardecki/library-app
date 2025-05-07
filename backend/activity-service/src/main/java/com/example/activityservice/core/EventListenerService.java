package com.example.activityservice.core;

import com.example.activityservice.domain.model.Activity;
import com.example.activityservice.domain.ports.out.ActivityRepositoryPort;
import com.example.activityservice.domain.ports.in.EventListenerPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class EventListenerService implements EventListenerPort {
    private final ActivityRepositoryPort activityRepository;
    private final ActivityFactory activityFactory;

    @Override
    public <T> void handle(T event) {
        Activity activity = activityFactory.createActivity(event);
        activityRepository.save(activity);
    }
}