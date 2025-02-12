package com.example.libraryapp.infrastructure.events.publishers;

public interface EventPublisherPort {
    void publish(Object event);
}
