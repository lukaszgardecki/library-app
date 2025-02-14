package com.example.libraryapp.domain.event.types.user;

import lombok.Getter;

@Getter
public class UserLogoutEvent extends UserEvent {

    public UserLogoutEvent(Long userId, String userFirstName, String userLastName) {
        super(userId, userFirstName, userLastName);
    }
}