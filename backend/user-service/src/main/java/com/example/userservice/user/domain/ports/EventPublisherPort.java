package com.example.userservice.user.domain.ports;

public interface EventPublisherPort {
    void publish(String topic, Object event);
}
