package com.example.fineservice.infrastructure.events;

import com.example.fineservice.core.FineFacade;
import com.example.fineservice.domain.dto.BookItemLoanDto;
import com.example.fineservice.domain.event.incoming.BookItemLostEvent;
import com.example.fineservice.domain.event.incoming.BookItemReturnedEvent;
import com.example.fineservice.domain.model.*;
import com.example.fineservice.domain.ports.EventListenerPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventListenerAdapter implements EventListenerPort {
    private final FineFacade fineFacade;

    private static final String BOOK_ITEM_RETURNED_SUCCESS_TOPIC = "book-item.returned.success";
    private static final String BOOK_ITEM_LOST_TOPIC = "book-item.lost";

    @Override
    @KafkaListener(topics = BOOK_ITEM_RETURNED_SUCCESS_TOPIC, groupId = "fine-service-listeners")
    public void processFineForBookReturn(BookItemLoanDto bookItemLoan) {
        fineFacade.processBookItemReturn(
                new LoanId(bookItemLoan.id()),
                new UserId(bookItemLoan.userId()),
                new LoanReturnDate(bookItemLoan.returnDate()),
                new LoanDueDate(bookItemLoan.dueDate()));
    }

    @Override
    @KafkaListener(topics = BOOK_ITEM_LOST_TOPIC, groupId = "fine-service-listeners")
    public void processFineForBookLost(BookItemLoanDto bookItemLoan, Price charge) {
        fineFacade.processBookItemLost(
                new LoanId(bookItemLoan.id()),
                new UserId(bookItemLoan.userId()),
                new LoanReturnDate(bookItemLoan.returnDate()),
                new LoanDueDate(bookItemLoan.dueDate()),
                charge
        );
    }
}
