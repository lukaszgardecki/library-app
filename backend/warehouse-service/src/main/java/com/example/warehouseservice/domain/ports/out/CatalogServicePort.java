package com.example.warehouseservice.domain.ports.out;

import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;

public interface CatalogServicePort {

    Long countBookItemsByParams(RackId rackId, ShelfId shelfId);
}
