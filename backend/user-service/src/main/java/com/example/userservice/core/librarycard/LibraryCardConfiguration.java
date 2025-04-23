package com.example.userservice.core.librarycard;

import com.example.userservice.domain.ports.LibraryCardRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LibraryCardConfiguration {

//    public LibraryCardFacade LibraryCardFacade() {
//        InMemoryLibraryCardRepositoryAdapter cardRepository = new InMemoryLibraryCardRepositoryAdapter();
//        LibraryCardService libraryCardService = new LibraryCardService(cardRepository);
//        return new LibraryCardFacade(
//                new CreateNewLibraryCardUseCase(libraryCardService),
//                new SaveLibraryCardUseCase(libraryCardService),
//                new GetLibraryCardUseCase(libraryCardService),
//                new ActivateLibraryCardUseCase(libraryCardService),
//                new BlockLibraryCardUseCase(libraryCardService),
//                new ReportLostLibraryCardUseCase(libraryCardService)
//        );
//    }

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
