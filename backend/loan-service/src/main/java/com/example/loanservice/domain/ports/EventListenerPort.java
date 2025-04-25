package com.example.loanservice.domain.ports;

import com.example.loanservice.domain.model.BookItemId;

public interface EventListenerPort {

    void handleBookItemReservedEvent(BookItemId bookItemId);
}
