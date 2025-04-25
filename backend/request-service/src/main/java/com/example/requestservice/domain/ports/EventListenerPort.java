package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.RequestId;

public interface EventListenerPort {

    void handleBookItemLoanedEvent(RequestId requestId);

    void handleBookItemReturnedEvent(BookItemId bookItemId);

    void handleBookItemLostEvent(BookItemId bookItemId);

    void handleBookItemDeletedEvent(BookItemId bookItemId);
}
