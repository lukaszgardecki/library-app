package com.example.warehouseservice.infrastructure.integration.catalogservice;

import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;
import com.example.warehouseservice.domain.ports.out.CatalogServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class CatalogServiceAdapter implements CatalogServicePort {
    private final CatalogServiceFeignClient client;

    @Override
    public Long countBookItemsByParams(RackId rackId, ShelfId shelfId) {
        ResponseEntity<Long> response = client.countByParams(rackId.value(), shelfId.value());
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        }
        return null;
    }
}
