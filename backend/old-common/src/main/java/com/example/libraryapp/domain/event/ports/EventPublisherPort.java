package com.example.libraryapp.domain.event.ports;

public interface EventPublisherPort {
    void publish(Object event);
}
