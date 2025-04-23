package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.auth.UserId;

public interface EventPublisherPort {

    void publishLoginSuccessEvent(UserId userId);

    void publishLoginFailureEvent(UserId userId);

    void publishLogoutSuccessEvent(UserId userId);
}
