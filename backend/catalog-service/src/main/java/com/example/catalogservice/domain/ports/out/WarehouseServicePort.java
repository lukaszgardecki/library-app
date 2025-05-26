package com.example.catalogservice.domain.ports.out;

import com.example.catalogservice.domain.integration.warehouse.rack.Rack;
import com.example.catalogservice.domain.integration.warehouse.shelf.Shelf;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;

public interface WarehouseServicePort {
    Rack getRackById(RackId rackId);

    Shelf getShelfById(ShelfId shelfId);
}
