package com.example.loanservice.infrastructure.events;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.event.outgoing.*;
import com.example.loanservice.domain.model.BookItemId;
import com.example.loanservice.domain.model.Price;
import com.example.loanservice.domain.model.RequestId;
import com.example.loanservice.domain.model.UserId;
import com.example.loanservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final KafkaTemplate<String, Object> template;

    private static final String LOAN_CREATED_TOPIC = "loan-service.loan.created";
    private static final String LOAN_PROLONGATION_NOT_ALLOWED_TOPIC = "loan-service.loan.prolongation.not-allowed";
    private static final String LOAN_PROLONGED_TOPIC = "loan-service.loan.prolonged";
    private static final String BOOK_ITEM_RETURNED_TOPIC = "loan-service.book-item.returned";
    private static final String BOOK_ITEM_LOST_TOPIC = "loan-service.book-item.lost";

    @Override
    public void publishLoanCreatedEvent(BookItemLoanDto bookItemLoan, RequestId requestId) {
        template.send(LOAN_CREATED_TOPIC, new LoanCreatedEvent(bookItemLoan, requestId));
    }

    @Override
    public void publishLoanProlongationNotAllowedEvent(BookItemId bookItemId, UserId userId) {
        template.send(LOAN_PROLONGATION_NOT_ALLOWED_TOPIC, new LoanProlongationNotAllowed(bookItemId, userId));
    }

    @Override
    public void publishLoanProlongedEvent(BookItemLoanDto bookItemLoan) {
        template.send(LOAN_PROLONGED_TOPIC, new LoanProlongedEvent(bookItemLoan));
    }

    @Override
    public void publishBookItemReturnedEvent(BookItemLoanDto bookItemLoan) {
        template.send(BOOK_ITEM_RETURNED_TOPIC, new BookItemReturnedEvent(bookItemLoan));
    }

    @Override
    public void publishBookItemLostEvent(BookItemLoanDto bookItemLoan, Price charge) {
        template.send(BOOK_ITEM_LOST_TOPIC, new BookItemLostEvent(bookItemLoan, charge));
    }
}
