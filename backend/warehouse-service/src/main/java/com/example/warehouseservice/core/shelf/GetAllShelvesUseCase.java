package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.Shelf;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class GetAllShelvesUseCase {
    private final ShelfService shelfService;

    Page<Shelf> execute(RackId rackId, String query, Pageable pageable) {
        return shelfService.findAllByParams(rackId, query, pageable);
    }

    List<Shelf> execute(RackId rackId, String query) {
        return shelfService.findAllByParams(rackId, query);
    }
}
