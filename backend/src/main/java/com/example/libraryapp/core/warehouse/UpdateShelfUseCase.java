package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import com.example.libraryapp.domain.shelf.model.ShelfUpdatedDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class UpdateShelfUseCase {
    private final ShelfService shelfService;

    Shelf execute(ShelfId shelfId, Shelf shelfFields) {
        Shelf shelfToUpdate = shelfService.getShelfById(shelfId);
        if (shelfFields.getName() != null) shelfToUpdate.setName(shelfFields.getName());
        shelfToUpdate.setUpdatedDate(new ShelfUpdatedDate(LocalDateTime.now()));
        return shelfService.save(shelfToUpdate);
    }
}
