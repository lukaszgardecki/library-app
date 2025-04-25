package com.example.paymentservice.core;

import com.example.paymentservice.domain.ports.PaymentRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

//    public PaymentFacade paymentFacade() {
//        InMemoryPaymentRepositoryAdapter paymentRepository = new InMemoryPaymentRepositoryAdapter();
//        PaymentRequestFactory requestFactory = new PaymentRequestFactory();
//        PaymentPortFactory portFactory = new PaymentPortFactory();
//        PaymentService paymentService = new PaymentService(paymentRepository);
//        return new PaymentFacade(
//                new ProcessPaymentUseCase(requestFactory, portFactory, paymentService),
//                new GetPaymentUseCase(paymentService),
//                new GetAllUserPaymentsUseCase(paymentService)
//        );
//    }

    @Bean
    public PaymentFacade paymentFacade(PaymentRepositoryPort paymentRepository) {
        PaymentRequestFactory requestFactory = new PaymentRequestFactory();
        PaymentPortFactory portFactory = new PaymentPortFactory();
        PaymentService paymentService = new PaymentService(paymentRepository);
        return new PaymentFacade(
                new ProcessPaymentUseCase(requestFactory, portFactory, paymentService),
                new GetPaymentUseCase(paymentService),
                new GetAllUserPaymentsUseCase(paymentService)
        );
    }
}
