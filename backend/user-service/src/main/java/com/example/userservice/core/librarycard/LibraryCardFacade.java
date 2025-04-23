package com.example.userservice.core.librarycard;

import com.example.userservice.domain.dto.librarycard.LibraryCardDto;
import com.example.userservice.domain.model.librarycard.LibraryCard;
import com.example.userservice.domain.model.librarycard.LibraryCardId;
import com.example.userservice.domain.model.user.UserId;
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

    public LibraryCardDto save(LibraryCardDto card) {
        LibraryCard cardToSave = LibraryCardMapper.toModel(card);
        LibraryCard savedCard = saveLibraryCardUseCase.execute(cardToSave);
        return LibraryCardMapper.toDto(savedCard);
    }

    public LibraryCardDto getLibraryCard(LibraryCardId id) {
        LibraryCard card = getLibraryCardUseCase.execute(id);
        return LibraryCardMapper.toDto(card);
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
