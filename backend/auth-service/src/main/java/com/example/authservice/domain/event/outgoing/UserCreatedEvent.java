package com.example.authservice.domain.event.outgoing;

import com.example.authservice.domain.integration.user.PersonFirstName;
import com.example.authservice.domain.integration.user.PersonLastName;
import com.example.authservice.domain.model.authdetails.values.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Getter
@RequiredArgsConstructor
public class UserCreatedEvent {
    private final UserId userId;
    private final PersonFirstName firstName;
    private final PersonLastName lastName;
    private final LocalDate birthday;
    private final String addressCity;
}