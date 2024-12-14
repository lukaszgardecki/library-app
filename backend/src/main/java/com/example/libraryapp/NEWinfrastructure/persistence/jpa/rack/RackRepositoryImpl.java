package com.example.libraryapp.NEWinfrastructure.persistence.jpa.rack;

import com.example.libraryapp.NEWdomain.rack.model.Rack;
import com.example.libraryapp.NEWdomain.rack.ports.RackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class RackRepositoryImpl implements RackRepository {
    private final JpaRackRepository repository;


    @Override
    public Optional<Rack> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<Rack> findByLocation(String location) {
        return repository.findByLocationIdentifier(location).map(this::toModel);
    }

    @Override
    @Transactional
    public Rack save(Rack rack) {
        return toModel(repository.save(toEntity(rack)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private RackEntity toEntity(Rack dto) {
        return new RackEntity(
                dto.getId(),
                dto.getLocationIdentifier()
        );
    }

    private Rack toModel(RackEntity entity) {
        return new Rack(
                entity.getId(),
                entity.getLocationIdentifier()
        );
    }
}
