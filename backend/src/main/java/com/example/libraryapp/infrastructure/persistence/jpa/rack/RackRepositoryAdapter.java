package com.example.libraryapp.infrastructure.persistence.jpa.rack;

import com.example.libraryapp.domain.rack.model.Rack;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.rack.model.RackLocationId;
import com.example.libraryapp.domain.rack.ports.RackRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class RackRepositoryAdapter implements RackRepositoryPort {
    private final JpaRackRepository repository;


    @Override
    public Optional<Rack> findById(RackId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Optional<Rack> findByLocation(RackLocationId location) {
        return repository.findByLocationIdentifier(location.value()).map(this::toModel);
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
                model.getLocationIdentifier().value()
        );
    }

    private Rack toModel(RackEntity entity) {
        return new Rack(
                new RackId(entity.getId()),
                new RackLocationId(entity.getLocationIdentifier())
        );
    }
}
