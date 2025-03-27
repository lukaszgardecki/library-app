package com.example.libraryapp.application.rack;

import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.model.RackLocationId;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RackFacade {
    private final GetRackUseCase getRackUseCase;
    private final AddRackUseCase addRackUseCase;
    private final DeleteRackUseCase deleteRackUseCase;

    public RackDto getRack(RackId id) {
        Rack rack = getRackUseCase.execute(id);
        return RackMapper.toDto(rack);
    }

    public RackDto getRackByLocation(RackLocationId location) {
        Rack rack = getRackUseCase.execute(location);
        return RackMapper.toDto(rack);
    }

    public RackDto addRack(RackToSaveDto dto) {
        Rack rack = RackMapper.toModel(dto);
        Rack savedRack = addRackUseCase.execute(rack);
        return RackMapper.toDto(savedRack);
    }

    public void deleteRack(RackId id) {
        deleteRackUseCase.execute(id);
    }
}
