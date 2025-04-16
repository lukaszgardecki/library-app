package com.example.libraryapp.application.useractivity;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.ports.UserActivityEventListenerPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class UserActivityEventListenerAdapter implements UserActivityEventListenerPort {
    private final UserActivityFacade userActivityFacade;
    private final MessageProviderPort messageProvider;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return ActivityFactory.getKeys();
    }

    @Override
    public void onEvent(CustomEvent event) {
        // TODO: 13.02.2025 usunąć ten sout
        System.out.printf("Zapisuję aktywność do bazy (%s)%n", event.getClass().getSimpleName());
        UserActivity activity = ActivityFactory.createActivity(event, messageProvider);
        userActivityFacade.saveActivity(UserActivityMapper.toDto(activity));
    }
}
