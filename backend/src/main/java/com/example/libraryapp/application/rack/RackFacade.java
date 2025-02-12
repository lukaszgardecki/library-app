package com.example.libraryapp.application.rack;

import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import com.example.libraryapp.domain.rack.model.Rack;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RackFacade {
    private final GetRackUseCase getRackUseCase;
    private final AddRackUseCase addRackUseCase;
    private final DeleteRackUseCase deleteRackUseCase;

    public RackDto getRack(Long id) {
        Rack rack = getRackUseCase.execute(id);
        return RackMapper.toDto(rack);
    }

    public RackDto getRackByLocation(String location) {
        Rack rack = getRackUseCase.execute(location);
        return RackMapper.toDto(rack);
    }

    public RackDto addRack(RackToSaveDto dto) {
        Rack rack = RackMapper.toModel(dto);
        Rack savedRack = addRackUseCase.execute(rack);
        return RackMapper.toDto(savedRack);
    }

    public void deleteRack(Long id) {
        deleteRackUseCase.execute(id);
    }
}
