package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.values.RackId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetRackUseCase {
    private final RackService rackService;

    Rack execute(RackId id) {
        return rackService.getRackById(id);
    }
}

