package com.example.libraryapp.domain.event.types.user;

import lombok.Getter;

@Getter
public class UserAuthSuccessEvent extends UserEvent {

    public UserAuthSuccessEvent(Long userId, String userFirstName, String userLastName) {
        super(userId, userFirstName, userLastName);
    }
}
