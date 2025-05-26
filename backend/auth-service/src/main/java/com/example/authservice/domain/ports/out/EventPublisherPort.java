package com.example.authservice.domain.ports.out;

import com.example.authservice.domain.model.authdetails.values.UserId;

public interface EventPublisherPort {

    void publishUserCreatedEvent(UserId userId);

    void publishLoginSuccessEvent(UserId userId);

    void publishLoginFailureEvent(UserId userId);

    void publishLogoutSuccessEvent(UserId userId);
}
