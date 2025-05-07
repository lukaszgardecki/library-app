package com.example.requestservice.domain.ports.in;

import com.example.requestservice.domain.integration.catalog.BookId;
import com.example.requestservice.domain.model.values.BookItemId;
import com.example.requestservice.domain.model.values.RequestId;

public interface EventListenerPort {

    void handleBookItemDeletedEvent(BookItemId bookItemId, BookId bookId);

    void handleLoanCreatedEvent(RequestId requestId);

    void handleBookItemReturnedEvent(BookItemId bookItemId);

    void handleBookItemLostEvent(BookItemId bookItemId, BookId bookId);
}
