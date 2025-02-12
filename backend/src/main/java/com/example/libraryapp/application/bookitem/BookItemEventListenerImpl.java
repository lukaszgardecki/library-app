package com.example.libraryapp.application.bookitem;

import com.example.libraryapp.domain.user.ports.UserEventListenerPort;
import com.example.libraryapp.infrastructure.events.event.CustomEvent;
import com.example.libraryapp.infrastructure.events.event.bookitem.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class BookItemEventListenerImpl implements UserEventListenerPort {
    private final BookItemService bookItemService;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemRequestedEvent.class,
                BookItemRequestCanceledEvent.class,
                BookItemReturnedEvent.class,
                BookItemLostEvent.class,
                BookItemRenewedEvent.class,
                BookItemLoanedEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemRequestedEvent e) {
            bookItemService.updateBookItemOnRequest(e.getBookItemId());
        } else if (event instanceof BookItemRequestCanceledEvent e) {
            bookItemService.updateBookItemOnRequestCancellation(e.getBookItemId());
        } else if (event instanceof BookItemReturnedEvent e) {
            bookItemService.updateBookItemOnReturn(e.getBookItemId(), e.getDueDate());
        } else if (event instanceof BookItemLostEvent e) {
            bookItemService.updateBookItemOnLoss(e.getBookItemId());
        } else if (event instanceof BookItemRenewedEvent e) {
            bookItemService.updateBookItemOnRenewal(e.getBookItemId(), e.getDueDate());
        } else if (event instanceof BookItemLoanedEvent e) {
            bookItemService.updateBookItemOnLoan(e.getBookItemId(), e.getCreationDate(), e.getDueDate());
        }
    }
}
