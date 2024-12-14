package com.example.libraryapp.NEWinfrastructure.events.listeners;

import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;

import java.util.List;

public interface EventListenerPort {
    List<Class<? extends CustomEvent>> getSupportedEventTypes();
    void onEvent(CustomEvent event);
}
