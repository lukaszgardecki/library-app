package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.model.PersonFirstName;
import com.example.notificationservice.domain.model.PersonLastName;
import com.example.notificationservice.domain.model.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    private UserId userId;
    private PersonFirstName firstName;
    private PersonLastName lastName;
}