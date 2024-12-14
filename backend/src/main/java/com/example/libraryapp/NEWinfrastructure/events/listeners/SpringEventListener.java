package com.example.libraryapp.NEWinfrastructure.events.listeners;

import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class SpringEventListener {
    private final List<EventListenerPort> eventListenerPorts;

    @EventListener
    public void handleEvents(CustomEvent event) {
        eventListenerPorts.stream()
                .filter(listener -> listener.getSupportedEventTypes().contains(event.getClass()))
                .forEach(listener -> listener.onEvent(event));
    }
}
