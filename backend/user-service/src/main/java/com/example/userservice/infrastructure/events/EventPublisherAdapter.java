package com.example.userservice.infrastructure.events;

import com.example.userservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    @Override
    public void publish(String topic, Object event) {
        template.send(topic, event);
    }
}
