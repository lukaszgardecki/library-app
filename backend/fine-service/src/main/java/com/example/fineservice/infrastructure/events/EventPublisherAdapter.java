package com.example.fineservice.infrastructure.events;

import com.example.fineservice.domain.event.outgoing.FinePaidEvent;
import com.example.fineservice.domain.model.FineAmount;
import com.example.fineservice.domain.model.UserId;
import com.example.fineservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String FINE_PAID_TOPIC = "fine-service.fine.paid";

    @Override
    public void publishFinePaidEvent(UserId userId, FineAmount amount) {
        template.send(FINE_PAID_TOPIC, new FinePaidEvent(userId, amount));
    }
}
