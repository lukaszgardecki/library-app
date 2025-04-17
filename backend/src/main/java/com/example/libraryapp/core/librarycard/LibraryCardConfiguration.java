package com.example.libraryapp.core.librarycard;

import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepositoryPort;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryLibraryCardRepositoryAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryCardConfiguration {

    public LibraryCardFacade LibraryCardFacade() {
        InMemoryLibraryCardRepositoryAdapter cardRepository = new InMemoryLibraryCardRepositoryAdapter();
        LibraryCardService libraryCardService = new LibraryCardService(cardRepository);
        return new LibraryCardFacade(
                new CreateNewLibraryCardUseCase(libraryCardService),
                new SaveLibraryCardUseCase(libraryCardService),
                new GetLibraryCardUseCase(libraryCardService),
                new ActivateLibraryCardUseCase(libraryCardService),
                new BlockLibraryCardUseCase(libraryCardService),
                new ReportLostLibraryCardUseCase(libraryCardService)
        );
    }

    @Bean
    public LibraryCardFacade libraryCardFacade(
            LibraryCardRepositoryPort cardRepository
    ) {
        LibraryCardService libraryCardService = new LibraryCardService(cardRepository);
        return new LibraryCardFacade(
                new CreateNewLibraryCardUseCase(libraryCardService),
                new SaveLibraryCardUseCase(libraryCardService),
                new GetLibraryCardUseCase(libraryCardService),
                new ActivateLibraryCardUseCase(libraryCardService),
                new BlockLibraryCardUseCase(libraryCardService),
                new ReportLostLibraryCardUseCase(libraryCardService)
        );
    }
}
