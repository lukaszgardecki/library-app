package com.example.libraryapp.domain.event.types.user;

import com.example.libraryapp.domain.person.model.PersonFirstName;
import com.example.libraryapp.domain.person.model.PersonLastName;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class UserLogoutEvent extends UserEvent {

    public UserLogoutEvent(UserId userId, PersonFirstName userFirstName, PersonLastName userLastName) {
        super(userId, userFirstName, userLastName);
    }
}