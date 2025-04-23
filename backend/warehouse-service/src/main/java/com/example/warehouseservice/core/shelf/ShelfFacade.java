package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.model.rack.RackId;
import com.example.warehouseservice.domain.dto.ShelfDto;
import com.example.warehouseservice.domain.dto.ShelfToSaveDto;
import com.example.warehouseservice.domain.model.shelf.Shelf;
import com.example.warehouseservice.domain.model.shelf.ShelfId;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
public class ShelfFacade {
    private final GetAllShelvesUseCase getAllShelvesUseCase;
    private final GetShelfUseCase getShelfUseCase;
    private final AddShelfUseCase addShelfUseCase;
    private final UpdateShelfUseCase updateShelfUseCase;
    private final DeleteShelfUseCase deleteShelfUseCase;

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

    public ShelfDto getShelfById(ShelfId id) {
        Shelf shelf = getShelfUseCase.execute(id);
        return ShelfMapper.toDto(shelf);
    }


    public ShelfDto addShelf(ShelfToSaveDto dto) {
        Shelf shelf = ShelfMapper.toModel(dto);
        Shelf savedShelf = addShelfUseCase.execute(shelf);
        return ShelfMapper.toDto(savedShelf);
    }

    public ShelfDto updateShelf(ShelfId shelfId, ShelfToSaveDto dto) {
        Shelf shelf = ShelfMapper.toModel(dto);
        Shelf updatedShelf = updateShelfUseCase.execute(shelfId, shelf);
        return ShelfMapper.toDto(updatedShelf);
    }

    public void deleteShelf(ShelfId id) {
        deleteShelfUseCase.execute(id);
    }

}
