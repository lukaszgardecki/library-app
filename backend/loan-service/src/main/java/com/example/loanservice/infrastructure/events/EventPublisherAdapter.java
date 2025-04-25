package com.example.loanservice.infrastructure.events;

import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.event.outgoing.BookItemLoanedEvent;
import com.example.loanservice.domain.event.incoming.BookItemLostEvent;
import com.example.loanservice.domain.event.outgoing.BookItemRenewalImpossibleEvent;
import com.example.loanservice.domain.event.outgoing.BookItemRenewedEvent;
import com.example.loanservice.domain.event.outgoing.BookItemReturnedEvent;
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

    private static final String BOOK_ITEM_LOANED_SUCCESS_TOPIC = "book-item.loaned.success";
    private static final String BOOK_ITEM_RENEWAL_IMPOSSIBLE_TOPIC = "book-item.renewal.impossible";
    private static final String BOOK_ITEM_RENEWED_SUCCESS_TOPIC = "book-item.renewed.success";
    private static final String BOOK_ITEM_RETURNED_SUCCESS_TOPIC = "book-item.returned.success";
    private static final String BOOK_ITEM_LOST_TOPIC = "book-item.lost";

    @Override
    public void publishBookItemLoanedEvent(BookItemLoanDto bookItemLoan, RequestId requestId) {
        template.send(BOOK_ITEM_LOANED_SUCCESS_TOPIC, new BookItemLoanedEvent(bookItemLoan, requestId));
    }

    @Override
    public void publishBookItemRenewalImpossibleEvent(BookItemId bookItemId, UserId userId) {
        template.send(BOOK_ITEM_RENEWAL_IMPOSSIBLE_TOPIC, new BookItemRenewalImpossibleEvent(bookItemId, userId));
    }

    @Override
    public void publishBookItemRenewedEvent(BookItemLoanDto bookItemLoan) {
        template.send(BOOK_ITEM_RENEWED_SUCCESS_TOPIC, new BookItemRenewedEvent(bookItemLoan));
    }

    @Override
    public void publishBookItemReturnedEvent(BookItemLoanDto bookItemLoan) {
        template.send(BOOK_ITEM_RETURNED_SUCCESS_TOPIC, new BookItemReturnedEvent(bookItemLoan));
    }

    @Override
    public void publishBookItemLostEvent(BookItemLoanDto bookItemLoan, Price charge) {
        template.send(BOOK_ITEM_LOST_TOPIC, new BookItemLostEvent(bookItemLoan, charge));
    }
}
