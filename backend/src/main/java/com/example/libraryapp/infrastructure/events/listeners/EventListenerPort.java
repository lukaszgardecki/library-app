package com.example.libraryapp.infrastructure.events.listeners;

import com.example.libraryapp.infrastructure.events.event.CustomEvent;

import java.util.List;

public interface EventListenerPort {
    List<Class<? extends CustomEvent>> getSupportedEventTypes();
    void onEvent(CustomEvent event);
}
