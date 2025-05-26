package com.example.paymentservice.core;

import com.example.paymentservice.domain.ports.out.PaymentRepositoryPort;
import com.example.paymentservice.domain.ports.out.PaymentStrategyPort;
import com.example.paymentservice.domain.ports.out.SourceValidatorPort;
import com.example.paymentservice.domain.request.PaymentRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class PaymentConfiguration {

    @Bean
    public PaymentFacade paymentFacade(
            PaymentRepositoryPort paymentRepository,
            SourceValidatorPort sourceValidator,
            PaymentProcessor portFactory
    ) {
        PaymentRequestFactory requestFactory = new PaymentRequestFactory();
        PaymentService paymentService = new PaymentService(paymentRepository);
        return new PaymentFacade(
                new ProcessPaymentUseCase(requestFactory, portFactory, paymentService),
                new GetPaymentUseCase(paymentService, sourceValidator),
                new GetAllUserPaymentsUseCase(paymentService)
        );
    }

    @Bean
    public PaymentProcessor paymentService(List<PaymentStrategyPort<? extends PaymentRequest>> strategies) {
        return new PaymentProcessor(strategies);
    }
}
