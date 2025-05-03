package com.example.fineservice.core;

import com.example.fineservice.domain.ports.EventListenerPort;
import com.example.fineservice.domain.ports.EventPublisherPort;
import com.example.fineservice.domain.ports.FineRepositoryPort;
import com.example.fineservice.domain.ports.PaymentServicePort;
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
