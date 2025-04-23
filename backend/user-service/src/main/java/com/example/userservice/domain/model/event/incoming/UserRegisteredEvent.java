package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.person.PersonFirstName;
import com.example.userservice.domain.model.person.PersonLastName;
import com.example.userservice.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class UserRegisteredEvent extends UserEvent {

    public UserRegisteredEvent(UserId userId, PersonFirstName userFirstName, PersonLastName userLastName) {
        super(userId, userFirstName, userLastName);
    }
}