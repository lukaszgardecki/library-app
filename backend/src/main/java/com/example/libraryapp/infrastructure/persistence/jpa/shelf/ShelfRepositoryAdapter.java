package com.example.libraryapp.infrastructure.persistence.jpa.shelf;

import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.*;
import com.example.libraryapp.domain.shelf.ports.ShelfRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
class ShelfRepositoryAdapter implements ShelfRepositoryPort {
    private final JpaShelfRepository repository;


    @Override
    public Page<Shelf> findAllByParams(RackId rackId, String query, Pageable pageable) {
        return repository.findAllByParams(rackId.value(), query, pageable).map(this::toModel);
    }

    private ShelfEntity toEntity(Shelf model) {
        return new ShelfEntity(
                model.getId() != null ? model.getId().value() : null,
                model.getName().value(),
                model.getPosition().value(),
                model.getCreatedDate().value(),
                model.getUpdatedDate().value(),
                model.getBookItemsCount().value(),
                model.getRackId().value()
        );
    }

    private Shelf toModel(ShelfEntity entity) {
        return new Shelf(
                new ShelfId(entity.getId()),
                new ShelfName(entity.getName()),
                new ShelfPosition(entity.getPosition()),
                new ShelfCreatedDate(entity.getCreatedDate()),
                new ShelfUpdatedDate(entity.getUpdatedDate()),
                new RackId(entity.getRackId()),
                new BookItemsCount(entity.getBookItemsCount())
        );
    }
}
