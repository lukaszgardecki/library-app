package com.example.catalogservice.infrastructure.integration.warehouseservice;

import com.example.catalogservice.domain.integration.warehouse.dto.RackDto;
import com.example.catalogservice.domain.integration.warehouse.dto.ShelfDto;
import com.example.catalogservice.domain.model.bookitem.values.RackId;
import com.example.catalogservice.domain.model.bookitem.values.ShelfId;
import com.example.catalogservice.domain.ports.out.WarehouseServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WarehouseServiceAdapter implements WarehouseServicePort {
    private final WarehouseServiceFeignClient client;

    @Override
    public RackDto getRackById(RackId rackId) {
        ResponseEntity<RackDto> response = client.getRackById(rackId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }

    @Override
    public ShelfDto getShelfById(ShelfId shelfId) {
        ResponseEntity<ShelfDto> response = client.getShelfById(shelfId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
