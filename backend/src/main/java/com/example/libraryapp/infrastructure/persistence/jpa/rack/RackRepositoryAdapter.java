package com.example.libraryapp.infrastructure.persistence.jpa.rack;

import com.example.libraryapp.domain.rack.model.*;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class RackRepositoryAdapter implements RackRepositoryPort {
    private final JpaRackRepository repository;


    @Override
    public Page<Rack> findAllByParams(String query, Pageable pageable) {
        return repository.findAllByParams(query, pageable).map(this::toModel);
    }

    @Override
    public Optional<Rack> findById(RackId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    @Transactional
    public Rack save(Rack rack) {
        return toModel(repository.save(toEntity(rack)));
    }

    @Override
    @Transactional
    public void deleteById(RackId id) {
        repository.deleteById(id.value());
    }

    private RackEntity toEntity(Rack model) {
        return new RackEntity(
                model.getId() != null ? model.getId().value() : null,
                model.getLocation().value(),
                model.getName().value(),
                model.getCreatedDate().value(),
                model.getUpdatedDate().value(),
                model.getShelvesCount().value()
        );
    }

    private Rack toModel(RackEntity entity) {
        return new Rack(
                new RackId(entity.getId()),
                new RackLocationId(entity.getLocation()),
                new RackName(entity.getName()),
                new RackCreatedDate(entity.getCreatedDate()),
                new RackUpdatedDate(entity.getUpdatedDate()),
                new RackShelvesCount(entity.getShelvesCount())
        );
    }
}
