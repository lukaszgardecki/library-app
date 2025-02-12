package com.example.libraryapp.application.fine;

import com.example.libraryapp.application.payment.PaymentConfiguration;
import com.example.libraryapp.application.payment.PaymentFacade;
import com.example.libraryapp.domain.fine.ports.FineRepository;
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
