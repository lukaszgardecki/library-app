package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class GetRackUseCase {
    private final RackService rackService;

    Rack execute(RackId id) {
        return rackService.getRackById(id);
    }
}

