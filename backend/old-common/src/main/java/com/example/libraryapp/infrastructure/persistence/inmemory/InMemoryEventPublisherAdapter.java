package com.example.libraryapp.infrastructure.persistence.inmemory;

import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class InMemoryEventPublisherAdapter implements EventPublisherPort {
    private final List<Object> eventStore = new ArrayList<>();

    @Override
    public void publish(Object event) {
        eventStore.add(event);
    }

    // Opcjonalna metoda pomocnicza, żeby sprawdzić przechowywane zdarzenia
    public List<Object> getEventStore() {
        return eventStore;
    }
}
