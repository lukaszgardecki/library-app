package com.example.authservice.infrastructure.events;

import com.example.authservice.domain.model.authdetails.UserId;
import com.example.authservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String LOGIN_SUCCESS_TOPIC = "auth-service.login.success";
    private static final String LOGIN_FAILURE_TOPIC = "auth-service.login.failure";
    private static final String LOGOUT_SUCCESS_TOPIC = "auth-service.logout.success";

    @Override
    public void publishLoginSuccessEvent(UserId userId) {
        template.send(LOGIN_SUCCESS_TOPIC, userId);
    }

    @Override
    public void publishLoginFailureEvent(UserId userId) {
        template.send(LOGIN_FAILURE_TOPIC, userId);
    }

    @Override
    public void publishLogoutSuccessEvent(UserId userId) {
        template.send(LOGOUT_SUCCESS_TOPIC, userId);
    }
}
