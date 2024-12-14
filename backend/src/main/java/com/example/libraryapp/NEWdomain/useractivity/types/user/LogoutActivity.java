package com.example.libraryapp.NEWdomain.useractivity.types.user;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import com.example.libraryapp.NEWdomain.useractivity.model.UserActivityType;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserEvent;

public class LogoutActivity extends UserActivity {

    public LogoutActivity(UserEvent event) {
        super(event.getUserId());
        this.type = UserActivityType.LOGOUT;
        this.message = "Message.ACTION_LOGOUT.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
    }

//    public LogoutActivity(Long userId, String userFirstName, String userLastName, String userCardBarcode) {
//        super(userId);
//        this.type = UserActivityType.LOGOUT;
//        this.message = "Message.ACTION_LOGOUT.getMessage(member.getFirstName(), member.getLastName(), member.getCard().getBarcode())";
//    }
}
