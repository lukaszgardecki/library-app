package com.example.paymentservice.core;

import com.example.paymentservice.domain.ports.PaymentRepositoryPort;
import com.example.paymentservice.domain.ports.SourceValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    public PaymentFacade paymentFacade(
            PaymentRepositoryPort paymentRepository,
            SourceValidator sourceValidator
    ) {
        PaymentRequestFactory requestFactory = new PaymentRequestFactory();
        PaymentPortFactory portFactory = new PaymentPortFactory();
        PaymentService paymentService = new PaymentService(paymentRepository);
        return new PaymentFacade(
                new ProcessPaymentUseCase(requestFactory, portFactory, paymentService),
                new GetPaymentUseCase(paymentService, sourceValidator),
                new GetAllUserPaymentsUseCase(paymentService)
        );
    }
}
