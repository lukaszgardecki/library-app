package com.example.catalogservice.domain.ports;

import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.LoanCreationDate;
import com.example.catalogservice.domain.model.bookitem.LoanDueDate;
import com.example.catalogservice.domain.model.LoanReturnDate;

public interface EventListenerPort {
        void handleRequestCreatedEvent(BookItemId bookItemId);
        void handleRequestCanceledEvent(BookItemId bookItemId);
        void handleLoanCreatedEvent(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate);
        void handleLoanProlongedEvent(BookItemId bookItemId, LoanDueDate dueDate);
        void handleBookItemReturnedEvent(BookItemId bookItemId, LoanReturnDate dueDate);
        void handleBookItemLostEvent(BookItemId bookItemId);
}
