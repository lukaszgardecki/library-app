package com.example.userservice.domain.ports;

public interface EventPublisherPort {
    void publish(String topic, Object event);
}
