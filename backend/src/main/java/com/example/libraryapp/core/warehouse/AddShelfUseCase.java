package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.shelf.model.*;
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
