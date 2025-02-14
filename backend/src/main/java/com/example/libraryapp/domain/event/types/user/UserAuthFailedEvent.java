package com.example.libraryapp.domain.event.types.user;

import lombok.Getter;

@Getter
public class UserAuthFailedEvent extends UserEvent {

    public UserAuthFailedEvent(Long userId, String userFirstName, String userLastName) {
        super(userId, userFirstName, userLastName);
    }
}



