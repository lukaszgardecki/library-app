package com.example.libraryapp.domain.useractivity.types.user;

import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.message.ports.MessageProviderPort;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.model.UserActivityType;
import com.example.libraryapp.domain.event.types.user.UserEvent;

public class AccountCreatedActivity extends UserActivity {

    public AccountCreatedActivity(UserEvent event, MessageProviderPort msgProvider) {
        super(event.getUserId());
        this.type = UserActivityType.REGISTER;
        this.message = new UserActivityMessage(msgProvider.getMessage(
                MessageKey.ACTIVITY_REGISTER, event.getUserFirstName().value(), event.getUserLastName().value()
        ));
    }
}
