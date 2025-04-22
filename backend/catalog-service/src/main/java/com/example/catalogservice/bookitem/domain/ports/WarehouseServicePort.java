package com.example.catalogservice.bookitem.domain.ports;

import com.example.catalogservice.bookitem.domain.dto.RackDto;
import com.example.catalogservice.bookitem.domain.dto.ShelfDto;
import com.example.catalogservice.bookitem.domain.model.RackId;
import com.example.catalogservice.bookitem.domain.model.ShelfId;

public interface WarehouseServicePort {
    RackDto getRackById(RackId rackId);

    ShelfDto getShelfById(ShelfId shelfId);
}
