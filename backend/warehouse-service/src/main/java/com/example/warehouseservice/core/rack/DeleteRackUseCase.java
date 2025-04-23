package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.model.rack.RackId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class DeleteRackUseCase {
    private final RackService rackService;

    void execute(RackId id) {
        rackService.verifyRackToDelete(id);
        rackService.deleteById(id);
    }
}
