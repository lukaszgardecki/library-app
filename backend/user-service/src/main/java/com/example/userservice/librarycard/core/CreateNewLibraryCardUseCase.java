package com.example.userservice.librarycard.core;

import com.example.userservice.librarycard.domain.model.LibraryCardId;
import com.example.userservice.user.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateNewLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCardId execute(UserId userId) {
        return libraryCardService.createLibraryCard(userId);
    }
}
