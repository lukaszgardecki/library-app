package com.example.requestservice.domain.ports;

import com.example.requestservice.domain.model.BookId;
import com.example.requestservice.domain.model.BookItemId;
import com.example.requestservice.domain.model.RequestId;

public interface EventListenerPort {

    void handleBookItemDeletedEvent(BookItemId bookItemId, BookId bookId);

    void handleLoanCreatedEvent(RequestId requestId);

    void handleBookItemReturnedEvent(BookItemId bookItemId);

    void handleBookItemLostEvent(BookItemId bookItemId, BookId bookId);
}
