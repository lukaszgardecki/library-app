package com.example.libraryapp.application.bookitemloan;

import com.example.libraryapp.domain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.domain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.domain.bookitemloan.ports.BookItemLoanListenerPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.domain.event.types.CustomEvent;
import com.example.libraryapp.domain.event.types.bookitem.BookItemRenewalImpossibleEvent;
import com.example.libraryapp.domain.event.types.bookitem.BookItemReservedEvent;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
class BookItemLoanEventListenerAdapter implements BookItemLoanListenerPort {
    private final BookItemLoanService bookItemLoanService;
    private final EventPublisherPort publisher;

    @Override
    public List<Class<? extends CustomEvent>> getSupportedEventTypes() {
        return List.of(
                BookItemReservedEvent.class
        );
    }

    @Override
    public void onEvent(CustomEvent event) {
        if (event instanceof BookItemReservedEvent e) {
            BookItemLoan currentLoan = bookItemLoanService.getBookItemLoan(e.getBookItemId(), BookItemLoanStatus.CURRENT);
            publisher.publish(new BookItemRenewalImpossibleEvent(e.getBookItemId(), currentLoan.getUserId(), e.getBookTitle()));
        }
    }
}
