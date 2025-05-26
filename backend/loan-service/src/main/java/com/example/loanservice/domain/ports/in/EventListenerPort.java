package com.example.loanservice.domain.ports.in;

import com.example.loanservice.domain.model.values.BookItemId;

public interface EventListenerPort {

    void handleBookItemReservedEvent(BookItemId bookItemId);
}
