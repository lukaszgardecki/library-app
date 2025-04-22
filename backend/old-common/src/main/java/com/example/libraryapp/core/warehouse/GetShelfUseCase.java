package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetShelfUseCase {
    private final ShelfService shelfService;

    Shelf execute(ShelfId id) {
        return shelfService.getShelfById(id);
    }
}
