package com.example.catalogservice.domain.ports.out;

import com.example.catalogservice.domain.integration.warehouse.dto.RackDto;
import com.example.catalogservice.domain.integration.warehouse.dto.ShelfDto;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;

public interface WarehouseServicePort {
    RackDto getRackById(RackId rackId);

    ShelfDto getShelfById(ShelfId shelfId);
}
