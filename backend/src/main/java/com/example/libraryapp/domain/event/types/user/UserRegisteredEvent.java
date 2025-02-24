package com.example.libraryapp.domain.event.types.user;

import lombok.Getter;

@Getter
public class UserRegisteredEvent extends UserEvent {

    public UserRegisteredEvent(Long userId, String userFirstName, String userLastName) {
        super(userId, userFirstName, userLastName);
    }
}