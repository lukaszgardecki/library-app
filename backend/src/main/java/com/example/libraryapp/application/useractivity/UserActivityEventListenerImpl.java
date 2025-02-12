package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityEventListenerPort;
import com.example.libraryapp.infrastructure.events.event.CustomEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class UserActivityEventListenerImpl implements UserActivityEventListenerPort {
    private final UserActivityFacade userActivityFacade;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return ActivityFactory.getKeys();
    }

    @Override
    public void onEvent(CustomEvent event) {
        System.out.printf("Zapisuję aktywność do bazy (%s)%n", event.getClass().getSimpleName());
        UserActivity activity = ActivityFactory.createActivity(event);
        userActivityFacade.saveActivity(UserActivityMapper.toDto(activity));
    }
}
