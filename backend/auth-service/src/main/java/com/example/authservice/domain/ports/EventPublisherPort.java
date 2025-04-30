package com.example.authservice.domain.ports;

import com.example.authservice.domain.model.authdetails.UserId;

public interface EventPublisherPort {

    void publishLoginSuccessEvent(UserId userId);

    void publishLoginFailureEvent(UserId userId);

    void publishLogoutSuccessEvent(UserId userId);
}
