package com.example.catalogservice.infrastructure.integration.warehouseservice;

import com.example.catalogservice.domain.integration.warehouse.rack.Rack;
import com.example.catalogservice.domain.integration.warehouse.shelf.Shelf;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import com.example.catalogservice.domain.ports.out.WarehouseServicePort;
import com.example.catalogservice.infrastructure.integration.warehouseservice.dto.RackDto;
import com.example.catalogservice.infrastructure.integration.warehouseservice.dto.ShelfDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WarehouseServiceAdapter implements WarehouseServicePort {
    private final WarehouseServiceFeignClient client;

    @Override
    public Rack getRackById(RackId rackId) {
        ResponseEntity<RackDto> response = client.getRackById(rackId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return WarehouseMapper.toModel(response.getBody());
        }
        return null;
    }

    @Override
    public Shelf getShelfById(ShelfId shelfId) {
        ResponseEntity<ShelfDto> response = client.getShelfById(shelfId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return WarehouseMapper.toModel(response.getBody());
        }
        return null;
    }
}
