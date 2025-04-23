package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.RackId;
import com.example.warehouseservice.domain.model.rack.RackUpdatedDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class UpdateRackUseCase {
    private final RackService rackService;

    Rack execute(RackId rackId, Rack rackFields) {
        Rack rackToUpdate = rackService.getRackById(rackId);
        if (rackFields.getName() != null) rackToUpdate.setName(rackFields.getName());
        rackToUpdate.setUpdatedDate(new RackUpdatedDate(LocalDateTime.now()));
        return rackService.save(rackToUpdate);
    }
}
