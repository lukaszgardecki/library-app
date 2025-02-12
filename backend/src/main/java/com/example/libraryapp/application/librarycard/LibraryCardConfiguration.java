package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.ports.LibraryCardRepository;
import com.example.libraryapp.infrastructure.persistence.inmemory.InMemoryLibraryCardRepositoryImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryCardConfiguration {

    public LibraryCardFacade LibraryCardFacade() {
        InMemoryLibraryCardRepositoryImpl cardRepository = new InMemoryLibraryCardRepositoryImpl();
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
            LibraryCardRepository cardRepository
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
