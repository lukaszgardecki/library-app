package com.example.libraryapp.domain.event.ports;

import com.example.libraryapp.domain.event.types.CustomEvent;

import java.util.List;

public interface EventListenerPort {
    List<Class<? extends CustomEvent>> getSupportedEventTypes();
    void onEvent(CustomEvent event);
}
