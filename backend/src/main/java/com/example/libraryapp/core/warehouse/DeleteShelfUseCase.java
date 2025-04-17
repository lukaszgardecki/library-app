package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.shelf.model.ShelfId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteShelfUseCase {
    private final ShelfService shelfService;

    void execute(ShelfId id) {
        shelfService.verifyShelfToDelete(id);
        shelfService.deleteById(id);
    }
}
