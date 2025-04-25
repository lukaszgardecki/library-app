package com.example.userservice.domain.ports;

import com.example.userservice.domain.model.person.PersonFirstName;
import com.example.userservice.domain.model.person.PersonLastName;
import com.example.userservice.domain.model.user.UserId;

public interface EventPublisherPort {

    void publishUserCreatedEvent(UserId userId, PersonFirstName firstName, PersonLastName lastName);
}
