package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.application.bookitem.BookItemFacade;
import com.example.libraryapp.domain.MessageKey;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.exceptions.ShelfException;
import com.example.libraryapp.domain.shelf.exceptions.ShelfNotFoundException;
import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import com.example.libraryapp.domain.shelf.ports.ShelfRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class ShelfService {
    private final ShelfRepositoryPort shelfRepository;
    private final BookItemFacade bookItemFacade;

    Shelf getShelfById(ShelfId id) {
        return shelfRepository.findById(id).orElseThrow(() -> new ShelfNotFoundException(id));
    }

    Shelf save(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    void verifyShelfToDelete(ShelfId shelfId) {
        Long bookItemsCount = bookItemFacade.countByParams(null, shelfId);
        if (bookItemsCount > 0) throw new ShelfException(MessageKey.SHELF_DELETION_FAILED);
    }

    void deleteById(ShelfId id) {
        shelfRepository.deleteById(id);
    }

    Integer getMaxPositionByRackId(RackId rackId) {
        return shelfRepository.findMaxPositionByRackId(rackId);
    }
}
