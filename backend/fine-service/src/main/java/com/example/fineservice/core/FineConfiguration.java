package com.example.fineservice.core;

import com.example.fineservice.domain.ports.EventPublisherPort;
import com.example.fineservice.domain.ports.FineRepositoryPort;
import com.example.fineservice.domain.ports.PaymentServicePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FineConfiguration {

//    public FineFacade fineFacade() {
//        InMemoryFineRepositoryAdapter fineRepository = new InMemoryFineRepositoryAdapter();
//        PaymentFacade paymentFacade = new PaymentConfiguration().paymentFacade();
//        FineService fineService = new FineService(fineRepository, paymentFacade);
//        InMemoryEventPublisherAdapter publisher = new InMemoryEventPublisherAdapter();
//        return new FineFacade(
//                new ValidateUserForFinesUseCase(fineService),
//                new ProcessBookItemReturnUseCase(fineService),
//                new ProcessBookItemLostUseCase(fineService),
//                new PayFineUseCase(fineService, publisher),
//                new CancelFineUseCase(fineRepository)
//        );
//    }

    @Bean
    FineFacade fineFacade(
            FineRepositoryPort fineRepository,
            PaymentServicePort paymentService,
            EventPublisherPort publisher
    ) {
        FineService fineService = new FineService(fineRepository, paymentService);
        return new FineFacade(
                new VerifyUserForFinesUseCase(fineService),
                new ProcessBookItemReturnUseCase(fineService),
                new ProcessBookItemLostUseCase(fineService),
                new PayFineUseCase(fineService, publisher),
                new CancelFineUseCase(fineRepository)
        );
    }
}
