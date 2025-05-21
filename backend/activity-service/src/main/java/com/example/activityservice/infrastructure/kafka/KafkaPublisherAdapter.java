package com.example.activityservice.infrastructure.kafka;

import com.example.activityservice.domain.ports.out.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

}
