package com.example.userservice.domain.event.outgoing;

import com.example.userservice.domain.model.person.PersonFirstName;
import com.example.userservice.domain.model.person.PersonLastName;
import com.example.userservice.domain.model.user.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class UserCreatedEvent {
    private final UserId userId;
    private final PersonFirstName firstName;
    private final PersonLastName lastName;
}