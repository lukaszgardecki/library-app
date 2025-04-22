package com.example.catalogservice.bookitem.infrastructure.integration.warehouseservice;

import com.example.catalogservice.bookitem.domain.dto.RackDto;
import com.example.catalogservice.bookitem.domain.dto.ShelfDto;
import com.example.catalogservice.bookitem.domain.model.RackId;
import com.example.catalogservice.bookitem.domain.model.ShelfId;
import com.example.catalogservice.bookitem.domain.ports.WarehouseServicePort;
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
