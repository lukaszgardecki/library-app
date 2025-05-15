package com.example.warehouseservice.core.shelf;

import com.example.warehouseservice.domain.model.rack.values.RackId;
import com.example.warehouseservice.domain.model.shelf.Shelf;
import com.example.warehouseservice.domain.model.shelf.values.ShelfId;
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

    public Page<Shelf> getAllShelvesPaged(
            @Nullable RackId rackId,
            @Nullable String query,
            Pageable pageable
    ) {
        return getAllShelvesUseCase.execute(rackId, query, pageable);
    }

    public List<Shelf> getAllShelvesList(
            @Nullable RackId rackId,
            @Nullable String query
    ) {
        return getAllShelvesUseCase.execute(rackId, query);
    }

    public Shelf getShelfById(ShelfId id) {
        return getShelfUseCase.execute(id);
    }


    public Shelf addShelf(Shelf shelf) {
        return addShelfUseCase.execute(shelf);
    }

    public Shelf updateShelf(ShelfId shelfId, Shelf shelf) {
        return updateShelfUseCase.execute(shelfId, shelf);
    }

    public void deleteShelf(ShelfId id) {
        deleteShelfUseCase.execute(id);
    }

}
