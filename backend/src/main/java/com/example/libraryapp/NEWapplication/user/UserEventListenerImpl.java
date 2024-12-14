package com.example.libraryapp.NEWapplication.user;

import com.example.libraryapp.NEWdomain.user.ports.UserEventListenerPort;
import com.example.libraryapp.NEWinfrastructure.events.event.bookitem.BookItemRequestedEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.CustomEvent;
import com.example.libraryapp.NEWinfrastructure.events.event.user.UserRegisteredEvent;

import java.util.List;


class UserEventListenerImpl implements UserEventListenerPort {

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemRequestedEvent.class,
                UserRegisteredEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemRequestedEvent eventObj) {
            System.out.println("Zarezerwowałem książkę id: " + eventObj.getBookItemId());
        } else if (event instanceof UserRegisteredEvent eventObj) {
//            System.out.println(eventObj.get());
        }
    }
}
