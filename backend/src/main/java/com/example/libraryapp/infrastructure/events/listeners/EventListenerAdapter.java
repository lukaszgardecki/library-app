package com.example.libraryapp.infrastructure.events.listeners;

import com.example.libraryapp.domain.event.ports.EventListenerPort;
import com.example.libraryapp.domain.event.types.CustomEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
class EventListenerAdapter {
    private final List<EventListenerPort> eventListenerPorts;

    @EventListener
    public void handleEvents(CustomEvent event) {
        eventListenerPorts.stream()
                .filter(listener -> listener.getSupportedEventTypes().contains(event.getClass()))
                .forEach(listener -> listener.onEvent(event));
    }
}
