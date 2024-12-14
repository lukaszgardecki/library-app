package com.example.libraryapp.NEWdomain.notification.types.user;


import com.example.libraryapp.NEWdomain.notification.model.Notification;
import com.example.libraryapp.NEWdomain.notification.model.NotificationType;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserRegisteredEvent;

public class UserRegisteredNotification extends Notification {

    public UserRegisteredNotification(UserRegisteredEvent event) {
        super(event.getUserId());
        this.type = NotificationType.USER_REGISTERED;
        this.subject = "Rejestrowanie użytkownika";
        this.content = "Cośtamw witaj pomyślnie założyłeś konto w naszej bibliotece";
    }

//    public UserRegisteredNotification(Long userId) {
//        super(userId);
//        this.type = NotificationType.USER_REGISTERED;
//        this.subject = "Rejestrowanie użytkownika";
//        this.content = "Cośtamw witaj pomyślnie założyłeś konto w naszej bibliotece";
//    }
}
