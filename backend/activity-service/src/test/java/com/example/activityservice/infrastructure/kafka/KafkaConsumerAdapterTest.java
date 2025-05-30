package com.example.activityservice.infrastructure.kafka;

import com.example.activityservice.domain.ports.in.EventListenerPort;
import com.example.activityservice.infrastructure.kafka.event.incoming.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class KafkaConsumerAdapterTest {

    @Mock
    private EventListenerPort eventListener;

    @InjectMocks
    private KafkaConsumerAdapter kafkaConsumerAdapter;

    @Test void shouldHandleUserCreated() {
        // given
        UserCreatedEvent event = new UserCreatedEvent();
        // when
        kafkaConsumerAdapter.userCreated(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleLoginSuccess() {
        // given
        LoginSuccessEvent event = new LoginSuccessEvent();
        // when
        kafkaConsumerAdapter.loginSuccess(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleLoginFailure() {
        // given
        LoginFailureEvent event = new LoginFailureEvent();
        // when
        kafkaConsumerAdapter.loginFailure(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleLogoutSuccess() {
        // given
        LogoutSuccessEvent event = new LogoutSuccessEvent();
        // when
        kafkaConsumerAdapter.logoutSuccess(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleRequestCreated() {
        // given
        RequestCreatedEvent event = new RequestCreatedEvent();
        // when
        kafkaConsumerAdapter.requestCreated(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleRequestReady() {
        // given
        RequestReadyEvent event = new RequestReadyEvent();
        // when
        kafkaConsumerAdapter.requestReady(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleRequestCanceled() {
        // given
        RequestCanceledEvent event = new RequestCanceledEvent();
        // when
        kafkaConsumerAdapter.requestCanceled(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleReservationCreated() {
        // given
        ReservationCreatedEvent event = new ReservationCreatedEvent();
        // when
        kafkaConsumerAdapter.reservationCreated(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleRequestAvailableToLoan() {
        // given
        RequestAvailableToLoanEvent event = new RequestAvailableToLoanEvent();
        // when
        kafkaConsumerAdapter.requestAvailableToLoan(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleLoanCreated() {
        // given
        LoanCreatedEvent event = new LoanCreatedEvent();
        // when
        kafkaConsumerAdapter.loanCreated(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleLoanProlonged() {
        // given
        LoanProlongedEvent event = new LoanProlongedEvent();
        // when
        kafkaConsumerAdapter.loanProlonged(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleLoanProlongationNotAllowed() {
        // given
        LoanProlongationNotAllowed event = new LoanProlongationNotAllowed();
        // when
        kafkaConsumerAdapter.loanProlongationNotAllowed(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleBookItemReturned() {
        // given
        BookItemReturnedEvent event = new BookItemReturnedEvent();
        // when
        kafkaConsumerAdapter.bookItemReturned(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleBookItemLost() {
        // given
        BookItemLostEvent event = new BookItemLostEvent();
        // when
        kafkaConsumerAdapter.bookItemLost(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleFinePaid() {
        // given
        FinePaidEvent event = new FinePaidEvent();
        // when
        kafkaConsumerAdapter.finePaid(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleSystemNotificationSent() {
        // given
        SystemNotificationSentEvent event = new SystemNotificationSentEvent();
        // when
        kafkaConsumerAdapter.systemNotificationSent(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleSmsNotificationSent() {
        // given
        SmsNotificationSentEvent event = new SmsNotificationSentEvent();
        // when
        kafkaConsumerAdapter.smsNotificationSent(event);
        // then
        verify(eventListener).handle(event);
    }

    @Test void shouldHandleEmailNotificationSent() {
        // given
        EmailNotificationSentEvent event = new EmailNotificationSentEvent();
        // when
        kafkaConsumerAdapter.emailNotificationSent(event);
        // then
        verify(eventListener).handle(event);
    }
}


