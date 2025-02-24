package com.example.libraryapp.domain.useractivity.types.user;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.event.types.user.UserEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;

public class LoginFailedActivity extends UserActivity {

    public LoginFailedActivity(UserEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.LOGIN_FAILED;
        this.message = msgProvider.getMessage(
                MessageKey.ACTIVITY_LOGIN_FAILED, event.getUserFirstName(), event.getUserLastName()
        );
    }
}
