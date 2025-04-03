package com.example.libraryapp.application.warehouse;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.dto.ShelfDto;
import com.example.libraryapp.domain.warehouse.dto.WarehouseBookItemRequestListViewDto;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class WarehouseFacade {
    private final GetBookItemRequestList getBookItemRequestList;

    private final GetAllRacksUseCase getAllRacksUseCase;
    private final GetAllShelvesUseCase getAllShelvesUseCase;
    private final GetRackUseCase getRackUseCase;
    private final AddRackUseCase addRackUseCase;
    private final DeleteRackUseCase deleteRackUseCase;

    public Page<WarehouseBookItemRequestListViewDto> getBookItemRequestList(BookItemRequestStatus status, Pageable pageable) {
        return getBookItemRequestList.execute(status, pageable)
                .map(BookItemRequestMapper::toRequestListViewDto);
    }

    public Page<RackDto> getAllRacks(@Nullable String query, Pageable pageable) {
        return getAllRacksUseCase.execute(query, pageable)
                .map(RackMapper::toDto);
    }

    public Page<ShelfDto> getAllShelves(
            @Nullable RackId rackId,
            @Nullable String query,
            Pageable pageable
    ) {
        return getAllShelvesUseCase.execute(rackId, query, pageable)
                .map(ShelfMapper::toDto);
    }

    public RackDto getRack(RackId id) {
        Rack rack = getRackUseCase.execute(id);
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
