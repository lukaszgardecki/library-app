package com.example.userservice.core.librarycard;

import com.example.userservice.domain.model.librarycard.LibraryCardId;
import com.example.userservice.domain.model.user.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateNewLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCardId execute(UserId userId) {
        return libraryCardService.createLibraryCard(userId);
    }
}
