package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.model.shelf.values.ShelfId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteShelfUseCase {
    private final ShelfService shelfService;

    void execute(ShelfId id) {
        shelfService.verifyShelfToDelete(id);
        shelfService.deleteById(id);
    }
}
