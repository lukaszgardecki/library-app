package com.example.requestservice.domain.ports.out;

import com.example.requestservice.domain.integration.warehouse.RackId;
import com.example.requestservice.domain.integration.warehouse.ShelfId;
import com.example.requestservice.domain.integration.warehouse.dto.RackDto;
import com.example.requestservice.domain.integration.warehouse.dto.ShelfDto;

public interface WarehouseServicePort {

    RackDto getRackById(RackId rackId);

    ShelfDto getShelfById(ShelfId shelfId);
}
