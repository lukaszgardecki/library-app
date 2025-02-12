package com.example.libraryapp.application.fine;

import com.example.libraryapp.application.payment.PaymentConfiguration;
import com.example.libraryapp.application.payment.PaymentFacade;
import com.example.libraryapp.domain.fine.ports.FineRepository;
import com.example.libraryapp.infrastructure.events.publishers.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherImpl;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryFineRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FineConfiguration {

    public FineFacade fineFacade() {
        InMemoryFineRepositoryImpl fineRepository = new InMemoryFineRepositoryImpl();
        PaymentFacade paymentFacade = new PaymentConfiguration().paymentFacade();
        FineService fineService = new FineService(fineRepository, paymentFacade);
        InMemoryEventPublisherImpl publisher = new InMemoryEventPublisherImpl();
        return new FineFacade(
                new ValidateUserForFinesUseCase(fineService),
                new ProcessBookItemReturnUseCase(fineService),
                new ProcessBookItemLostUseCase(fineService),
                new PayFineUseCase(fineService, publisher),
                new CancelFineUseCase(fineRepository)
        );
    }

    @Bean
    FineFacade fineFacade(
            FineRepository fineRepository,
            PaymentFacade paymentFacade,
            EventPublisherPort publisher
    ) {
        FineService fineService = new FineService(fineRepository, paymentFacade);
        return new FineFacade(
                new ValidateUserForFinesUseCase(fineService),
                new ProcessBookItemReturnUseCase(fineService),
                new ProcessBookItemLostUseCase(fineService),
                new PayFineUseCase(fineService, publisher),
                new CancelFineUseCase(fineRepository)
        );
    }
}
