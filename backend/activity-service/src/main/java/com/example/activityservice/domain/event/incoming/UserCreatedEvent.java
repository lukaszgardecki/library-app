package com.example.activityservice.domain.event.incoming;

import com.example.activityservice.domain.integration.user.PersonFirstName;
import com.example.activityservice.domain.integration.user.PersonLastName;
import com.example.activityservice.domain.model.values.UserId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreatedEvent {
    private UserId userId;
    private PersonFirstName firstName;
    private PersonLastName lastName;
    private LocalDate birthday;
    private String addressCity;
}