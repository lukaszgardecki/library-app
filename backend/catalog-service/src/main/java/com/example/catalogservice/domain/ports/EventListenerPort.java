package com.example.catalogservice.domain.ports;

import com.example.catalogservice.domain.model.bookitem.BookItemId;
import com.example.catalogservice.domain.model.bookitem.LoanCreationDate;
import com.example.catalogservice.domain.model.bookitem.LoanDueDate;
import com.example.catalogservice.domain.model.bookitem.LoanReturnDate;

public interface EventListenerPort {
        void updateBookItemOnRequest(BookItemId bookItemId);
        void updateBookItemOnRequestCancellation(BookItemId bookItemId);
        void updateBookItemOnReturn(BookItemId bookItemId, LoanReturnDate dueDate);
        void updateBookItemOnLoss(BookItemId bookItemId);
        void updateBookItemOnRenewal(BookItemId bookItemId, LoanDueDate dueDate);
        void updateBookItemOnLoan(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate);
}
