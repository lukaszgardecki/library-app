package com.example.authservice.infrastructure.kafka.event.outgoing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginSuccessEvent {
    private final Long userId;
    private final String firstName;
    private final String lastName;
}