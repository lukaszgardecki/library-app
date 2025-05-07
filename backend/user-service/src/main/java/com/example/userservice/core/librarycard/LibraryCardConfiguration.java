package com.example.userservice.core.librarycard;

import com.example.userservice.domain.ports.out.LibraryCardRepositoryPort;
import com.example.userservice.domain.ports.out.SourceValidatorPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryCardConfiguration {

    @Bean
    public LibraryCardFacade libraryCardFacade(
            LibraryCardRepositoryPort cardRepository,
            SourceValidatorPort sourceValidator
    ) {
        LibraryCardService libraryCardService = new LibraryCardService(cardRepository);
        return new LibraryCardFacade(
                new CreateNewLibraryCardUseCase(libraryCardService),
                new SaveLibraryCardUseCase(libraryCardService),
                new GetLibraryCardUseCase(libraryCardService, sourceValidator),
                new ActivateLibraryCardUseCase(libraryCardService),
                new BlockLibraryCardUseCase(libraryCardService),
                new ReportLostLibraryCardUseCase(libraryCardService)
        );
    }
}
