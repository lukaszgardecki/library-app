package com.example.statisticsservice.domain.event.incoming;

import com.example.statisticsservice.domain.model.borrower.values.PersonFirstName;
import com.example.statisticsservice.domain.model.borrower.values.PersonLastName;
import com.example.statisticsservice.domain.model.borrower.values.UserId;
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