package com.example.userservice.core.librarycard;

import com.example.userservice.domain.model.librarycard.values.LibraryCardId;
import com.example.userservice.domain.model.user.values.UserId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateNewLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    LibraryCardId execute(UserId userId) {
        return libraryCardService.createLibraryCard(userId);
    }
}
