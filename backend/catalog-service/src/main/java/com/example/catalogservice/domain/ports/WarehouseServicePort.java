package com.example.catalogservice.domain.ports;

import com.example.catalogservice.domain.dto.RackDto;
import com.example.catalogservice.domain.dto.ShelfDto;
import com.example.catalogservice.domain.model.bookitem.RackId;
import com.example.catalogservice.domain.model.bookitem.ShelfId;

public interface WarehouseServicePort {
    RackDto getRackById(RackId rackId);

    ShelfDto getShelfById(ShelfId shelfId);
}
