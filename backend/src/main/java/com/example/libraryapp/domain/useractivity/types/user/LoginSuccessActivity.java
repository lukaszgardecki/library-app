package com.example.libraryapp.domain.useractivity.types.user;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.event.types.user.UserEvent;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;

public class LoginSuccessActivity extends UserActivity {

    public LoginSuccessActivity(UserEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.LOGIN;
        this.message = new UserActivityMessage(msgProvider.getMessage(
                MessageKey.ACTIVITY_LOGIN_SUCCEEDED, event.getUserFirstName().value(), event.getUserLastName().value()
        ));
    }
}
