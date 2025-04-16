package com.example.libraryapp.domain.event.types.user;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.person.model.PersonFirstName;
import com.example.libraryapp.domain.person.model.PersonLastName;
import com.example.libraryapp.domain.user.model.UserId;
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
