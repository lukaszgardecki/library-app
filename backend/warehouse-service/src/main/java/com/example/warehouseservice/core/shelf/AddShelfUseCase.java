package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.model.shelf.*;
import com.example.warehouseservice.domain.model.shelf.values.BookItemsCount;
import com.example.warehouseservice.domain.model.shelf.values.ShelfCreatedDate;
import com.example.warehouseservice.domain.model.shelf.values.ShelfPosition;
import com.example.warehouseservice.domain.model.shelf.values.ShelfUpdatedDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class AddShelfUseCase {
    private final ShelfService shelfService;

    Shelf execute(Shelf shelf) {
        Integer lastPosition = shelfService.getMaxPositionByRackId(shelf.getRackId());
        shelf.setCreatedDate(new ShelfCreatedDate(LocalDateTime.now()));
        shelf.setUpdatedDate(new ShelfUpdatedDate(null));
        shelf.setPosition(new ShelfPosition(lastPosition + 1));
        shelf.setBookItemsCount(new BookItemsCount(0));
        return shelfService.save(shelf);
    }
}
