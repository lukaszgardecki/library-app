package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.authdetails.PersonFirstName;
import com.example.authservice.domain.model.authdetails.PersonLastName;
import com.example.authservice.domain.model.authdetails.UserId;

public interface EventPublisherPort {

    void publishUserCreatedEvent(UserId userId, PersonFirstName firstName, PersonLastName lastName);

    void publishLoginSuccessEvent(UserId userId);

    void publishLoginFailureEvent(UserId userId);

    void publishLogoutSuccessEvent(UserId userId);
}
