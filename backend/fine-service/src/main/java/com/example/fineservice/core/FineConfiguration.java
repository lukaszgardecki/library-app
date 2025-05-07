package com.example.fineservice.core;

import com.example.fineservice.domain.ports.in.EventListenerPort;
import com.example.fineservice.domain.ports.out.EventPublisherPort;
import com.example.fineservice.domain.ports.out.FineRepositoryPort;
import com.example.fineservice.domain.ports.out.PaymentServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FineConfiguration {

    @Bean
    FineFacade fineFacade(
            FineRepositoryPort fineRepository,
            PaymentServicePort paymentService,
            EventPublisherPort publisher
    ) {
        FineService fineService = new FineService(fineRepository, paymentService);
        return new FineFacade(
                new VerifyUserForFinesUseCase(fineService),
                new PayFineUseCase(fineService, publisher),
                new CancelFineUseCase(fineRepository)
        );
    }

    @Bean
    EventListenerPort eventListenerService(FineRepositoryPort fineRepository) {
        return new EventListenerService(fineRepository);
    }
}
