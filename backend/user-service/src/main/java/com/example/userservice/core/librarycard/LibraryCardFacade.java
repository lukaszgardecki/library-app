package com.example.userservice.core.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibraryCardFacade {
    private final CreateNewLibraryCardUseCase createNewLibraryCardUseCase;
    private final SaveLibraryCardUseCase saveLibraryCardUseCase;
    private final GetLibraryCardUseCase getLibraryCardUseCase;
    private final ActivateLibraryCardUseCase activateLibraryCardUseCase;
    private final BlockLibraryCardUseCase blockLibraryCardUseCase;
    private final ReportLostLibraryCardUseCase reportLostLibraryCardUseCase;

    public LibraryCardId createNewLibraryCard(UserId userId) {
        return createNewLibraryCardUseCase.execute(userId);
    }

    public LibraryCard save(LibraryCard card) {
        return saveLibraryCardUseCase.execute(card);
    }

    public LibraryCard getLibraryCard(LibraryCardId id) {
        return getLibraryCardUseCase.execute(id);
    }

    public void activateLibraryCard(LibraryCardId id) {
        activateLibraryCardUseCase.execute(id);
    }

    public void blockLibraryCard(LibraryCardId id) {
        blockLibraryCardUseCase.execute(id);
    }

    public void reportLostLibraryCard(LibraryCardId id) {
        reportLostLibraryCardUseCase.execute(id);
    }
}
