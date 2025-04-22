package com.example.userservice.user.domain.event.incoming;

import com.example.userservice.person.domain.model.PersonFirstName;
import com.example.userservice.person.domain.model.PersonLastName;
import com.example.userservice.user.domain.event.CustomEvent;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.Getter;

@Getter
public abstract class UserEvent extends CustomEvent {
    private final PersonFirstName userFirstName;
    private final PersonLastName userLastName;

    protected UserEvent(UserId userId, PersonFirstName userFirstName, PersonLastName userLastName) {
        super(userId);
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
    }
}
