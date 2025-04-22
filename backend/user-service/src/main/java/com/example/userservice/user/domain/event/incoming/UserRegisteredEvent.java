package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.person.domain.model.PersonFirstName;
import com.example.userservice.person.domain.model.PersonLastName;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public class UserRegisteredEvent extends UserEvent {

    public UserRegisteredEvent(UserId userId, PersonFirstName userFirstName, PersonLastName userLastName) {
        super(userId, userFirstName, userLastName);
    }
}