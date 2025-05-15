package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.exception.ShelfException;
import com.example.warehouseservice.domain.exception.ShelfNotFoundException;
import com.example.warehouseservice.domain.i18n.MessageKey;
import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.Shelf;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;
import com.example.warehouseservice.domain.ports.out.CatalogServicePort;
import com.example.warehouseservice.domain.ports.out.ShelfRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class ShelfService {
    private final ShelfRepositoryPort shelfRepository;
    private final CatalogServicePort bookItemService;

    Page<Shelf> findAllByParams(RackId rackId, String query, Pageable pageable) {
        return shelfRepository.findAllByParams(rackId, query, pageable);
    }

    List<Shelf> findAllByParams(RackId rackId, String query) {
        return shelfRepository.findAllByParams(rackId, query);
    }

    Shelf getShelfById(ShelfId id) {
        return shelfRepository.findById(id).orElseThrow(() -> new ShelfNotFoundException(id));
    }

    Shelf save(Shelf shelf) {
        return shelfRepository.save(shelf);
    }

    void verifyShelfToDelete(ShelfId shelfId) {
        Long bookItemsCount = bookItemService.countBookItemsByParams(null, shelfId);
        if (bookItemsCount > 0) throw new ShelfException(MessageKey.SHELF_DELETION_FAILED);
    }

    void deleteById(ShelfId id) {
        shelfRepository.deleteById(id);
    }

    Integer getMaxPositionByRackId(RackId rackId) {
        return shelfRepository.findMaxPositionByRackId(rackId);
    }
}
