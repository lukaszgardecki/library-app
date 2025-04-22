package com.example.libraryapp.core.warehouse;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.rack.dto.RackDto;
import com.example.libraryapp.domain.rack.dto.RackToSaveDto;
import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.dto.ShelfDto;
import com.example.libraryapp.domain.shelf.dto.ShelfToSaveDto;
import com.example.libraryapp.domain.shelf.model.Shelf;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import com.example.libraryapp.domain.warehouse.dto.WarehouseBookItemRequestListViewDto;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class WarehouseFacade {
    private final GetBookItemRequestList getBookItemRequestList;

    private final GetAllRacksUseCase getAllRacksUseCase;
    private final GetAllShelvesUseCase getAllShelvesUseCase;
    private final GetRackUseCase getRackUseCase;
    private final GetShelfUseCase getShelfUseCase;
    private final AddRackUseCase addRackUseCase;
    private final AddShelfUseCase addShelfUseCase;
    private final UpdateRackUseCase updateRackUseCase;
    private final UpdateShelfUseCase updateShelfUseCase;
    private final DeleteRackUseCase deleteRackUseCase;
    private final DeleteShelfUseCase deleteShelfUseCase;

    public Page<WarehouseBookItemRequestListViewDto> getBookItemRequestList(BookItemRequestStatus status, Pageable pageable) {
        return getBookItemRequestList.execute(status, pageable)
                .map(BookItemRequestMapper::toRequestListViewDto);
    }

    public Page<RackDto> getAllRacksPaged(@Nullable String query, Pageable pageable) {
        return getAllRacksUseCase.execute(query, pageable)
                .map(RackMapper::toDto);
    }

    public List<RackDto> getAllRacksList(String query) {
        return getAllRacksUseCase.execute(query).stream()
                .map(RackMapper::toDto)
                .toList();
    }

    public Page<ShelfDto> getAllShelvesPaged(
            @Nullable RackId rackId,
            @Nullable String query,
            Pageable pageable
    ) {
        return getAllShelvesUseCase.execute(rackId, query, pageable)
                .map(ShelfMapper::toDto);
    }

    public List<ShelfDto> getAllShelvesList(
            @Nullable RackId rackId,
            @Nullable String query
    ) {
        return getAllShelvesUseCase.execute(rackId, query)
                .stream()
                .map(ShelfMapper::toDto)
                .toList();
    }

    public RackDto getRack(RackId id) {
        Rack rack = getRackUseCase.execute(id);
        return RackMapper.toDto(rack);
    }

    public ShelfDto getShelf(ShelfId id) {
        Shelf shelf = getShelfUseCase.execute(id);
        return ShelfMapper.toDto(shelf);
    }

    public RackDto addRack(RackToSaveDto dto) {
        Rack rack = RackMapper.toModel(dto);
        Rack savedRack = addRackUseCase.execute(rack);
        return RackMapper.toDto(savedRack);
    }

    public ShelfDto addShelf(ShelfToSaveDto dto) {
        Shelf shelf = ShelfMapper.toModel(dto);
        Shelf savedShelf = addShelfUseCase.execute(shelf);
        return ShelfMapper.toDto(savedShelf);
    }

    public RackDto updateRack(RackId rackId, RackToSaveDto dto) {
        Rack rack = RackMapper.toModel(dto);
        Rack updatedRack = updateRackUseCase.execute(rackId, rack);
        return RackMapper.toDto(updatedRack);
    }

    public ShelfDto updateShelf(ShelfId shelfId, ShelfToSaveDto dto) {
        Shelf shelf = ShelfMapper.toModel(dto);
        Shelf updatedShelf = updateShelfUseCase.execute(shelfId, shelf);
        return ShelfMapper.toDto(updatedShelf);
    }

    public void deleteRack(RackId id) {
        deleteRackUseCase.execute(id);
    }

    public void deleteShelf(ShelfId id) {
        deleteShelfUseCase.execute(id);
    }

}
