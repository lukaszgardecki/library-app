package com.example.catalogservice.bookitem.domain.ports;

import com.example.catalogservice.bookitem.domain.model.BookItemId;
import com.example.catalogservice.bookitem.domain.model.LoanCreationDate;
import com.example.catalogservice.bookitem.domain.model.LoanDueDate;
import com.example.catalogservice.bookitem.domain.model.LoanReturnDate;

public interface EventListenerPort {
        void updateBookItemOnRequest(BookItemId bookItemId);
        void updateBookItemOnRequestCancellation(BookItemId bookItemId);
        void updateBookItemOnReturn(BookItemId bookItemId, LoanReturnDate dueDate);
        void updateBookItemOnLoss(BookItemId bookItemId);
        void updateBookItemOnRenewal(BookItemId bookItemId, LoanDueDate dueDate);
        void updateBookItemOnLoan(BookItemId bookItemId, LoanCreationDate creationDate, LoanDueDate dueDate);
}
