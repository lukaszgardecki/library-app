package com.example.libraryapp.core.fine;

import com.example.libraryapp.core.payment.PaymentConfiguration;
import com.example.libraryapp.core.payment.PaymentFacade;
import com.example.libraryapp.domain.fine.ports.FineRepositoryPort;
import com.example.libraryapp.domain.event.ports.EventPublisherPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryEventPublisherAdapter;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryFineRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FineConfiguration {

    public FineFacade fineFacade() {
        InMemoryFineRepositoryAdapter fineRepository = new InMemoryFineRepositoryAdapter();
        PaymentFacade paymentFacade = new PaymentConfiguration().paymentFacade();
        FineService fineService = new FineService(fineRepository, paymentFacade);
        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
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
            FineRepositoryPort fineRepository,
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
