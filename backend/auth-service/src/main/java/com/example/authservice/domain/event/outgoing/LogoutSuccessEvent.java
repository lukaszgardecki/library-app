package com.example.authservice.domain.event.outgoing;

import com.example.authservice.domain.model.authdetails.PersonFirstName;
import com.example.authservice.domain.model.authdetails.PersonLastName;
import com.example.authservice.domain.model.authdetails.UserId;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LogoutSuccessEvent {
    private final UserId userId;
    private final PersonFirstName firstName;
    private final PersonLastName lastName;
}