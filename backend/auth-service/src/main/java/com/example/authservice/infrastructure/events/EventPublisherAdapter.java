package com.example.authservice.infrastructure.events;

import com.example.authservice.domain.dto.PersonDto;
import com.example.authservice.domain.event.outgoing.LoginFailureEvent;
import com.example.authservice.domain.event.outgoing.LoginSuccessEvent;
import com.example.authservice.domain.event.outgoing.LogoutSuccessEvent;
import com.example.authservice.domain.event.outgoing.UserCreatedEvent;
import com.example.authservice.domain.integration.user.PersonFirstName;
import com.example.authservice.domain.integration.user.PersonLastName;
import com.example.authservice.domain.model.authdetails.values.UserId;
import com.example.authservice.domain.ports.out.EventPublisherPort;
import com.example.authservice.domain.ports.out.UserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final UserServicePort userService;
    private final KafkaTemplate<String, Object> template;

    private static final String USER_CREATED_TOPIC = "auth-service.user.created";
    private static final String LOGIN_SUCCESS_TOPIC = "auth-service.login.success";
    private static final String LOGIN_FAILURE_TOPIC = "auth-service.login.failure";
    private static final String LOGOUT_SUCCESS_TOPIC = "auth-service.logout.success";

    @Override
    public void publishUserCreatedEvent(UserId userId) {
        PersonDto person = userService.getPersonByUser(userId.value());
        template.send(USER_CREATED_TOPIC, new UserCreatedEvent(
                userId, new PersonFirstName(person.getFirstName()), new PersonLastName(person.getLastName()),
                person.getDateOfBirth(), person.getAddress().getCity()
        ));
    }

    @Override
    public void publishLoginSuccessEvent(UserId userId) {
        PersonDto person = userService.getPersonByUser(userId.value());
        template.send(LOGIN_SUCCESS_TOPIC, new LoginSuccessEvent(
                userId, new PersonFirstName(person.getFirstName()), new PersonLastName(person.getLastName())
        ));
    }

    @Override
    public void publishLoginFailureEvent(UserId userId) {
        PersonDto person = userService.getPersonByUser(userId.value());
        template.send(LOGIN_FAILURE_TOPIC, new LoginFailureEvent(
                userId, new PersonFirstName(person.getFirstName()), new PersonLastName(person.getLastName())
        ));
    }

    @Override
    public void publishLogoutSuccessEvent(UserId userId) {
        PersonDto person = userService.getPersonByUser(userId.value());
        template.send(LOGOUT_SUCCESS_TOPIC, new LogoutSuccessEvent(
                userId, new PersonFirstName(person.getFirstName()), new PersonLastName(person.getLastName())
        ));
    }
}
