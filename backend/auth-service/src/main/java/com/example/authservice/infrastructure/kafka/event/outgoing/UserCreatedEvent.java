package com.example.authservice.infrastructure.kafka.event.outgoing;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class UserCreatedEvent {
    private final Long userId;
    private final String firstName;
    private final String lastName;
    private final LocalDate birthday;
    private final String addressCity;
}