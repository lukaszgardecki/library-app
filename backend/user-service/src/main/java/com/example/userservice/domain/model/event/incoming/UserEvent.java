package com.example.userservice.domain.model.event.incoming;

import com.example.userservice.domain.model.event.CustomEvent;
import com.example.userservice.domain.model.person.PersonFirstName;
import com.example.userservice.domain.model.person.PersonLastName;
import com.example.userservice.domain.model.user.UserId;
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
