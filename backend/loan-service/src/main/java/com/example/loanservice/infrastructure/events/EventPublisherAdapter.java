package com.example.loanservice.infrastructure.events;

import com.example.loanservice.domain.dto.BookDto;
import com.example.loanservice.domain.dto.BookItemLoanDto;
import com.example.loanservice.domain.event.outgoing.*;
import com.example.loanservice.domain.model.*;
import com.example.loanservice.domain.ports.CatalogServicePort;
import com.example.loanservice.domain.ports.EventPublisherPort;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class EventPublisherAdapter implements EventPublisherPort {
    private final CatalogServicePort catalogService;
    private final KafkaTemplate<String, Object> template;

    private static final String LOAN_CREATED_TOPIC = "loan-service.loan.created";
    private static final String LOAN_PROLONGATION_NOT_ALLOWED_TOPIC = "loan-service.loan.prolongation.not-allowed";
    private static final String LOAN_PROLONGED_TOPIC = "loan-service.loan.prolonged";
    private static final String BOOK_ITEM_RETURNED_TOPIC = "loan-service.book-item.returned";
    private static final String BOOK_ITEM_LOST_TOPIC = "loan-service.book-item.lost";

    @Override
    public void publishLoanCreatedEvent(BookItemLoanDto bookItemLoan, RequestId requestId, Boolean isReferenceOnly) {
        BookDto book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.bookItemId()));
        template.send(LOAN_CREATED_TOPIC, new LoanCreatedEvent(
                bookItemLoan, requestId, isReferenceOnly, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishLoanProlongationNotAllowedEvent(BookItemId bookItemId, UserId userId) {
        BookDto book = catalogService.getBookByBookItemId(bookItemId);
        template.send(LOAN_PROLONGATION_NOT_ALLOWED_TOPIC, new LoanProlongationNotAllowed(
                bookItemId, userId, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishLoanProlongedEvent(BookItemLoanDto bookItemLoan) {
        BookDto book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.bookItemId()));
        template.send(LOAN_PROLONGED_TOPIC, new LoanProlongedEvent(
                bookItemLoan, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishBookItemReturnedEvent(BookItemLoanDto bookItemLoan) {
        BookDto book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.bookItemId()));
        template.send(BOOK_ITEM_RETURNED_TOPIC, new BookItemReturnedEvent(
                bookItemLoan, new Title(book.getTitle())
        ));
    }

    @Override
    public void publishBookItemLostEvent(BookItemLoanDto bookItemLoan, BookId bookId, Price charge) {
        BookDto book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.bookItemId()));
        template.send(BOOK_ITEM_LOST_TOPIC, new BookItemLostEvent(
                bookItemLoan, bookId, charge, new Title(book.getTitle())
        ));
    }
}
