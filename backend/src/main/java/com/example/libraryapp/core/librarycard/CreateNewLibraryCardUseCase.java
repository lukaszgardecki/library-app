package com.example.libraryapp.core.librarycard;

import com.example.libraryapp.domain.librarycard.model.LibraryCardId;
import com.example.libraryapp.domain.user.model.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateNewLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCardId execute(UserId userId) {
        return libraryCardService.createLibraryCard(userId);
    }
}
