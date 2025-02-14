package com.example.libraryapp.application.librarycard;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class CreateNewLibraryCardUseCase {
    private final LibraryCardService libraryCardService;

    Long execute(Long userId) {
        return libraryCardService.createLibraryCard(userId);
    }
}
