package com.example.libraryapp.domain.event.types.user;

import com.example.libraryapp.domain.person.model.PersonFirstName;
import com.example.libraryapp.domain.person.model.PersonLastName;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class UserRegisteredEvent extends UserEvent {

    public UserRegisteredEvent(UserId userId, PersonFirstName userFirstName, PersonLastName userLastName) {
        super(userId, userFirstName, userLastName);
    }
}