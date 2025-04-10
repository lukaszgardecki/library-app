package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.model.RackUpdatedDate;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
class UpdateRackUseCase {
    private final RackService rackService;

    Rack execute(RackId rackId, Rack rackFields) {
        Rack rackToUpdate = rackService.getRackById(rackId);
        if (rackFields.getLocation() != null) rackToUpdate.setLocation(rackFields.getLocation());
        if (rackFields.getName() != null) rackToUpdate.setName(rackFields.getName());
        rackToUpdate.setUpdatedDate(new RackUpdatedDate(LocalDateTime.now()));
        return rackService.save(rackToUpdate);
    }
}
