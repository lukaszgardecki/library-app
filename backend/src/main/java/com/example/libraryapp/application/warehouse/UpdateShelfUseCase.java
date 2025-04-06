package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class UpdateShelfUseCase {
    private final ShelfService shelfService;

    Shelf execute(ShelfId shelfId, Shelf shelfFields) {
        Shelf shelfToUpdate = shelfService.getShelfById(shelfId);
        if (shelfFields.getName() != null) shelfToUpdate.setName(shelfFields.getName());
        return shelfService.save(shelfToUpdate);
    }
}
