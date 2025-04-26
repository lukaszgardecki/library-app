package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.model.PersonFirstName;
import com.example.notificationservice.domain.model.PersonLastName;
import com.example.notificationservice.domain.model.UserId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserCreatedEvent {
    private UserId userId;
    private PersonFirstName firstName;
    private PersonLastName lastName;
}