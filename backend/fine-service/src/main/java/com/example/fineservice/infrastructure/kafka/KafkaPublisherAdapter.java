package com.example.fineservice.infrastructure.kafka;

import com.example.fineservice.domain.model.values.FineAmount;
import com.example.fineservice.domain.model.values.UserId;
import com.example.fineservice.domain.ports.out.EventPublisherPort;
import com.example.fineservice.infrastructure.kafka.event.outgoing.FinePaidEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String FINE_PAID_TOPIC = "fine-service.fine.paid";

    @Override
    public void publishFinePaidEvent(UserId userId, FineAmount amount) {
        template.send(FINE_PAID_TOPIC, new FinePaidEvent(userId.value(), amount.value()));
    }
}
