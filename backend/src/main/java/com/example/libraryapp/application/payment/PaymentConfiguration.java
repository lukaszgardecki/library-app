package com.example.libraryapp.application.payment;

import com.example.libraryapp.application.auth.AuthenticationConfiguration;
import com.example.libraryapp.application.auth.AuthenticationFacade;
import com.example.libraryapp.domain.payment.ports.PaymentRepository;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryPaymentRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    public PaymentFacade paymentFacade() {
        InMemoryPaymentRepositoryAdapter paymentRepository = new InMemoryPaymentRepositoryAdapter();
        PaymentRequestFactory requestFactory = new PaymentRequestFactory();
        PaymentPortFactory portFactory = new PaymentPortFactory();
        PaymentService paymentService = new PaymentService(paymentRepository);
        AuthenticationFacade authFacade = new AuthenticationConfiguration().authenticationFacade();
        return new PaymentFacade(
                new ProcessPaymentUseCase(requestFactory, portFactory, paymentService),
                new GetPaymentUseCase(paymentService),
                new GetAllUserPaymentsUseCase(paymentService, authFacade)
        );
    }

    @Bean
    public PaymentFacade paymentFacade(
        PaymentRepository paymentRepository,
        AuthenticationFacade authFacade
    ) {
        PaymentRequestFactory requestFactory = new PaymentRequestFactory();
        PaymentPortFactory portFactory = new PaymentPortFactory();
        PaymentService paymentService = new PaymentService(paymentRepository);
        return new PaymentFacade(
                new ProcessPaymentUseCase(requestFactory, portFactory, paymentService),
                new GetPaymentUseCase(paymentService),
                new GetAllUserPaymentsUseCase(paymentService, authFacade)
        );
    }
}
