package com.example.libraryapp.domain.event.types.user;

import com.example.libraryapp.domain.person.model.PersonFirstName;
import com.example.libraryapp.domain.person.model.PersonLastName;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.Getter;

@Getter
public class UserAuthSuccessEvent extends UserEvent {

    public UserAuthSuccessEvent(UserId userId, PersonFirstName userFirstName, PersonLastName userLastName) {
        super(userId, userFirstName, userLastName);
    }
}
