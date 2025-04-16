package com.example.libraryapp.application.librarycard;

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
