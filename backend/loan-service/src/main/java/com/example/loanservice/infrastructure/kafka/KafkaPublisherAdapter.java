package com.example.loanservice.infrastructure.kafka;

import com.example.loanservice.domain.integration.catalogservice.book.Book;
import com.example.loanservice.domain.integration.catalogservice.book.values.BookId;
import com.example.loanservice.domain.integration.catalogservice.bookitem.values.IsReferenceOnly;
import com.example.loanservice.domain.integration.catalogservice.bookitem.values.Price;
import com.example.loanservice.domain.integration.requestservice.RequestId;
import com.example.loanservice.domain.model.BookItemLoan;
import com.example.loanservice.domain.model.values.BookItemId;
import com.example.loanservice.domain.model.values.UserId;
import com.example.loanservice.domain.ports.out.CatalogServicePort;
import com.example.loanservice.domain.ports.out.EventPublisherPort;
import com.example.loanservice.infrastructure.http.BookItemLoanMapper;
import com.example.loanservice.infrastructure.http.dto.BookItemLoanDto;
import com.example.loanservice.infrastructure.kafka.event.outgoing.*;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class KafkaPublisherAdapter implements EventPublisherPort {
    private final CatalogServicePort catalogService;
    private final KafkaTemplate<String, Object> template;

    private static final String LOAN_CREATED_TOPIC = "loan-service.loan.created";
    private static final String LOAN_PROLONGATION_NOT_ALLOWED_TOPIC = "loan-service.loan.prolongation.not-allowed";
    private static final String LOAN_PROLONGED_TOPIC = "loan-service.loan.prolonged";
    private static final String BOOK_ITEM_RETURNED_TOPIC = "loan-service.book-item.returned";
    private static final String BOOK_ITEM_LOST_TOPIC = "loan-service.book-item.lost";

    @Override
    public void publishLoanCreatedEvent(BookItemLoan bookItemLoan, RequestId requestId, IsReferenceOnly isReferenceOnly) {
        Book book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.getBookItemId().value()));
        BookItemLoanDto loan = BookItemLoanMapper.toDto(bookItemLoan);
        template.send(LOAN_CREATED_TOPIC, new LoanCreatedEvent(
                loan, requestId.value(), isReferenceOnly.value(), book.getTitle().value(), book.getSubject().value()
        ));
    }

    @Override
    public void publishLoanProlongationNotAllowedEvent(BookItemId bookItemId, UserId userId) {
        Book book = catalogService.getBookByBookItemId(bookItemId);
        template.send(LOAN_PROLONGATION_NOT_ALLOWED_TOPIC, new LoanProlongationNotAllowed(
                bookItemId.value(), userId.value(), book.getTitle().value()
        ));
    }

    @Override
    public void publishLoanProlongedEvent(BookItemLoan bookItemLoan) {
        Book book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.getBookItemId().value()));
        BookItemLoanDto loan = BookItemLoanMapper.toDto(bookItemLoan);
        template.send(LOAN_PROLONGED_TOPIC, new LoanProlongedEvent(
                loan, book.getTitle().value()
        ));
    }

    @Override
    public void publishBookItemReturnedEvent(BookItemLoan bookItemLoan) {
        Book book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.getBookItemId().value()));
        BookItemLoanDto loan = BookItemLoanMapper.toDto(bookItemLoan);
        template.send(BOOK_ITEM_RETURNED_TOPIC, new BookItemReturnedEvent(
                loan, book.getTitle().value()
        ));
    }

    @Override
    public void publishBookItemLostEvent(BookItemLoan bookItemLoan, BookId bookId, Price charge) {
        Book book = catalogService.getBookByBookItemId(new BookItemId(bookItemLoan.getBookItemId().value()));
        BookItemLoanDto loan = BookItemLoanMapper.toDto(bookItemLoan);
        template.send(BOOK_ITEM_LOST_TOPIC, new BookItemLostEvent(
                loan, bookId.value(), charge.value(), book.getTitle().value()
        ));
    }
}
