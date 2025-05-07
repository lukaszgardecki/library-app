package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.model.PersonFirstName;
import com.example.activityservice.domain.model.PersonLastName;
import com.example.activityservice.domain.model.UserId;
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