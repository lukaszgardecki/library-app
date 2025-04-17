package com.example.libraryapp.core.user;

import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.event.types.bookitem.*;
import com.example.libraryapp.domain.user.ports.UserEventListenerPort;
import com.example.libraryapp.domain.event.types.fine.FinePaidEvent;
import com.example.libraryapp.domain.event.types.user.UserRegisteredEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;


@RequiredArgsConstructor
class UserEventListenerAdapter implements UserEventListenerPort {
    private final UserService userService;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                UserRegisteredEvent.class,
                BookItemRequestedEvent.class,
                BookItemRequestCanceledEvent.class,
                BookItemReturnedEvent.class,
                BookItemLostEvent.class,
                BookItemRenewedEvent.class,
                BookItemLoanedEvent.class,
                FinePaidEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof UserRegisteredEvent e) {

        } else if (event instanceof BookItemRequestedEvent e) {
            userService.updateUserOnRequest(e.getUserId());
        } else if (event instanceof BookItemRequestCanceledEvent e) {
            userService.updateUserOnRequestCancellation(e.getUserId());
        } else if (event instanceof BookItemReturnedEvent e) {
            userService.updateUserOnReturn(e.getUserId());
        } else if (event instanceof BookItemLostEvent e) {
            userService.updateUserOnLoss(e.getUserId());
        } else if (event instanceof BookItemRenewedEvent e) {
            userService.updateUserOnRenewal(e.getUserId());
        } else if (event instanceof BookItemLoanedEvent e) {
            userService.updateUserOnLoan(e.getUserId());
        } else if (event instanceof FinePaidEvent e) {
            userService.updateUserOnFinePaid(e.getUserId(), e.getFineAmount());
        }
    }
}
