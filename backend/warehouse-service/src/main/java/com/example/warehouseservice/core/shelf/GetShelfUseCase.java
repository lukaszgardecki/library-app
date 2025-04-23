package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.model.shelf.Shelf;
import com.example.warehouseservice.domain.model.shelf.ShelfId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetShelfUseCase {
    private final ShelfService shelfService;

    Shelf execute(ShelfId id) {
        return shelfService.getShelfById(id);
    }
}
