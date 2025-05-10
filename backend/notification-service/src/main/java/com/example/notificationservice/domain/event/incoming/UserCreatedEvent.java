package com.example.notificationservice.domain.event.incoming;

import com.example.notificationservice.domain.integration.user.PersonFirstName;
import com.example.notificationservice.domain.integration.user.PersonLastName;
import com.example.notificationservice.domain.model.values.UserId;
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