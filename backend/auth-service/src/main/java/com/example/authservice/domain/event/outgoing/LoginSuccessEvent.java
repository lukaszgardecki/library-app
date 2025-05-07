package com.example.authservice.domain.event.outgoing;

import com.example.authservice.domain.integration.user.PersonFirstName;
import com.example.authservice.domain.integration.user.PersonLastName;
import com.example.authservice.domain.model.authdetails.values.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginSuccessEvent {
    private final UserId userId;
    private final PersonFirstName firstName;
    private final PersonLastName lastName;
}