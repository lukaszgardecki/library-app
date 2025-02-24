package com.example.libraryapp.application.librarycard;

import com.example.libraryapp.domain.librarycard.dto.LibraryCardDto;
import com.example.libraryapp.domain.librarycard.model.LibraryCard;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LibraryCardFacade {
    private final CreateNewLibraryCardUseCase createNewLibraryCardUseCase;
    private final SaveLibraryCardUseCase saveLibraryCardUseCase;
    private final GetLibraryCardUseCase getLibraryCardUseCase;
    private final ActivateLibraryCardUseCase activateLibraryCardUseCase;
    private final BlockLibraryCardUseCase blockLibraryCardUseCase;
    private final ReportLostLibraryCardUseCase reportLostLibraryCardUseCase;

    public Long createNewLibraryCard(Long userId) {
        return createNewLibraryCardUseCase.execute(userId);
    }

    public LibraryCardDto save(LibraryCardDto card) {
        LibraryCard cardToSave = LibraryCardMapper.toModel(card);
        LibraryCard savedCard = saveLibraryCardUseCase.execute(cardToSave);
        return LibraryCardMapper.toDto(savedCard);
    }

    public LibraryCardDto getLibraryCard(Long id) {
        LibraryCard card = getLibraryCardUseCase.execute(id);
        return LibraryCardMapper.toDto(card);
    }

    public void activateLibraryCard(Long userId) {
        activateLibraryCardUseCase.execute(userId);
    }

    public void blockLibraryCard(Long userId) {
        blockLibraryCardUseCase.execute(userId);
    }

    public void reportLostLibraryCard(Long userId) {
        reportLostLibraryCardUseCase.execute(userId);
    }
}
