package com.example.warehouseservice.core.rack;

import com.example.warehouseservice.domain.dto.RackDto;
import com.example.warehouseservice.domain.dto.RackToSaveDto;
import com.example.warehouseservice.domain.model.rack.Rack;
import com.example.warehouseservice.domain.model.rack.values.RackId;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class RackFacade {
    private final GetAllRacksUseCase getAllRacksUseCase;
    private final GetRackUseCase getRackUseCase;
    private final AddRackUseCase addRackUseCase;
    private final UpdateRackUseCase updateRackUseCase;
    private final DeleteRackUseCase deleteRackUseCase;

    public Page<RackDto> getAllRacksPaged(@Nullable String query, Pageable pageable) {
        return getAllRacksUseCase.execute(query, pageable)
                .map(RackMapper::toDto);
    }

    public List<RackDto> getAllRacksList(String query) {
        return getAllRacksUseCase.execute(query).stream()
                .map(RackMapper::toDto)
                .toList();
    }

    public RackDto getRackById(RackId id) {
        Rack rack = getRackUseCase.execute(id);
        return RackMapper.toDto(rack);
    }

    public RackDto addRack(RackToSaveDto dto) {
        Rack rack = RackMapper.toModel(dto);
        Rack savedRack = addRackUseCase.execute(rack);
        return RackMapper.toDto(savedRack);
    }

    public RackDto updateRack(RackId rackId, RackToSaveDto dto) {
        Rack rack = RackMapper.toModel(dto);
        Rack updatedRack = updateRackUseCase.execute(rackId, rack);
        return RackMapper.toDto(updatedRack);
    }

    public void deleteRack(RackId id) {
        deleteRackUseCase.execute(id);
    }

}
