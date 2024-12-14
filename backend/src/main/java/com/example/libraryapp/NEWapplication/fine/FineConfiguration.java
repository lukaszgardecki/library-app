package com.example.libraryapp.NEWapplication.fine;

import com.example.libraryapp.NEWdomain.fine.ports.FineRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class FineConfiguration {

    @Bean
    FineFacade fineFacade(FineRepository fineRepository) {
        FineService fineService = new FineService(fineRepository);
        return new FineFacade(
                new ValidateUserForFinesUseCase(fineService),
                new ProcessBookItemReturnUseCase(fineService)
        );
    }
}
