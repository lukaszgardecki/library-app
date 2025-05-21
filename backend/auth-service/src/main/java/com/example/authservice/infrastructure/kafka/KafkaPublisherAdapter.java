package com.example.authservice.infrastructure.kafka;

import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.model.person.Person;
import com.example.authservice.domain.ports.out.EventPublisherPort;
import com.example.authservice.domain.ports.out.UserServicePort;
import com.example.authservice.infrastructure.kafka.event.outgoing.LoginFailureEvent;
import com.example.authservice.infrastructure.kafka.event.outgoing.LoginSuccessEvent;
import com.example.authservice.infrastructure.kafka.event.outgoing.LogoutSuccessEvent;
import com.example.authservice.infrastructure.kafka.event.outgoing.UserCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaPublisherAdapter implements EventPublisherPort {
    private final UserServicePort userService;
    private final KafkaTemplate<String, Object> template;

    private static final String USER_CREATED_TOPIC = "auth-service.user.created";
    private static final String LOGIN_SUCCESS_TOPIC = "auth-service.login.success";
    private static final String LOGIN_FAILURE_TOPIC = "auth-service.login.failure";
    private static final String LOGOUT_SUCCESS_TOPIC = "auth-service.logout.success";

    @Override
    public void publishUserCreatedEvent(UserId userId) {
        Person person = userService.getPersonByUser(userId.value());
        template.send(USER_CREATED_TOPIC, new UserCreatedEvent(
                userId.value(), person.getFirstName().value(), person.getLastName().value(),
                person.getDateOfBirth().value(), person.getAddress().getCity().value()
        ));
    }

    @Override
    public void publishLoginSuccessEvent(UserId userId) {
        Person person = userService.getPersonByUser(userId.value());
        template.send(LOGIN_SUCCESS_TOPIC, new LoginSuccessEvent(
                userId.value(), person.getFirstName().value(), person.getLastName().value()
        ));
    }

    @Override
    public void publishLoginFailureEvent(UserId userId) {
        Person person = userService.getPersonByUser(userId.value());
        template.send(LOGIN_FAILURE_TOPIC, new LoginFailureEvent(
                userId.value(), person.getFirstName().value(), person.getLastName().value()
        ));
    }

    @Override
    public void publishLogoutSuccessEvent(UserId userId) {
        Person person = userService.getPersonByUser(userId.value());
        template.send(LOGOUT_SUCCESS_TOPIC, new LogoutSuccessEvent(
                userId.value(), person.getFirstName().value(), person.getLastName().value()
        ));
    }
}
