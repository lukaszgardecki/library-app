package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.RequestId;

public interface EventListenerPort {

    void handleBookItemDeletedEvent(BookItemId bookItemId);

    void handleLoanCreatedEvent(RequestId requestId);

    void handleBookItemReturnedEvent(BookItemId bookItemId);

    void handleBookItemLostEvent(BookItemId bookItemId);
}
