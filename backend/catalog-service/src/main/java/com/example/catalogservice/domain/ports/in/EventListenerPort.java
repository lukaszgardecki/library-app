package com.example.catalogservice.domain.ports.in;

import com.example.catalogservice.domain.model.bookitem.values.BookItemId;
import com.example.catalogservice.domain.model.bookitem.values.LoanCreationDate;
import com.example.catalogservice.domain.model.bookitem.values.LoanDueDate;
import com.example.catalogservice.domain.integration.loan.LoanReturnDate;

public interface EventListenerPort {
        void handleRequestCreatedEvent(BookItemId bookItemId);
        void handleRequestCanceledEvent(BookItemId bookItemId);
        void handleLoanCreatedEvent(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate);
        void handleLoanProlongedEvent(BookItemId bookItemId, LoanDueDate dueDate);
        void handleBookItemReturnedEvent(BookItemId bookItemId, LoanReturnDate dueDate);
        void handleBookItemLostEvent(BookItemId bookItemId);
}
