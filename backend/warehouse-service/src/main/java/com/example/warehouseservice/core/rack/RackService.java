package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.i18n.MessageKey;
import com.example.warehouseservice.domain.exception.RackNotFoundException;
import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.ports.out.CatalogServicePort;
import com.example.warehouseservice.domain.ports.out.RackRepositoryPort;
import com.example.warehouseservice.domain.exception.ShelfException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
class RackService {
    private final RackRepositoryPort rackRepository;
    private final CatalogServicePort bookItemService;

    Page<Rack> findAllByParams(String query, Pageable pageable) {
        return rackRepository.findAllByParams(query, pageable);
    }

    List<Rack> findAllByParams(String query) {
        return rackRepository.findAllByParams(query);
    }

    Rack getRackById(RackId id) {
        return rackRepository.findById(id).orElseThrow(() -> new RackNotFoundException(id));
    }

    Rack save(Rack rack) {
        return rackRepository.save(rack);
    }

    void verifyRackToDelete(RackId rackId) {
        Long bookItemsCount = bookItemService.countBookItemsByParams(rackId, null);
        if (bookItemsCount > 0) throw new ShelfException(MessageKey.RACK_DELETION_FAILED);
    }

    void deleteById(RackId id) {
        rackRepository.deleteById(id);
    }
}
