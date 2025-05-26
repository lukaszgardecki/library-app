package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.model.shelf.Shelf;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;
import com.example.warehouseservice.domain.model.shelf.values.ShelfUpdatedDate;
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
