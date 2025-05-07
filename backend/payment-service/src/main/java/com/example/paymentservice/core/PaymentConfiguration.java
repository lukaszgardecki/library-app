package com.example.paymentservice.core;

import com.example.paymentservice.domain.ports.out.PaymentRepositoryPort;
import com.example.paymentservice.domain.ports.out.SourceValidatorPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    public PaymentFacade paymentFacade(
            PaymentRepositoryPort paymentRepository,
            SourceValidatorPort sourceValidator
    ) {
        PaymentRequestFactory requestFactory = new PaymentRequestFactory();
        PaymentFactory portFactory = new PaymentFactory();
        PaymentService paymentService = new PaymentService(paymentRepository);
        return new PaymentFacade(
                new ProcessPaymentUseCase(requestFactory, portFactory, paymentService),
                new GetPaymentUseCase(paymentService, sourceValidator),
                new GetAllUserPaymentsUseCase(paymentService)
        );
    }
}
