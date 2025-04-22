package com.example.authservice.auth.domain.ports;

import com.example.authservice.auth.domain.model.UserId;

public interface EventPublisherPort {

    void publishLoginSuccessEvent(UserId userId);

    void publishLoginFailureEvent(UserId userId);

    void publishLogoutSuccessEvent(UserId userId);
}
