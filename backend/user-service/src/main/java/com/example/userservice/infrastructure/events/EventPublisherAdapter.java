package com.example.userservice.infrastructure.events;

import com.example.userservice.domain.event.outgoing.UserCreatedEvent;
import com.example.userservice.domain.model.person.PersonFirstName;
import com.example.userservice.domain.model.person.PersonLastName;
import com.example.userservice.domain.model.user.UserId;
import com.example.userservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String USER_CREATED_TOPIC = "user-service.user.created";

    @Override
    public void publishUserCreatedEvent(UserId userId, PersonFirstName firstName, PersonLastName lastName) {
        template.send(USER_CREATED_TOPIC, new UserCreatedEvent(userId, firstName, lastName));
    }
}
