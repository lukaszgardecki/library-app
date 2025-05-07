package com.example.activityservice.infrastructure.persistence.jpa;

import com.example.activityservice.domain.model.*;
import com.example.activityservice.domain.ports.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class ActivityRepositoryAdapter implements ActivityRepositoryPort {
    private final JpaActivityRepository repository;

    @Override
    public Optional<Activity> findById(ActivityId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Page<Activity> findAllByParams(UserId userId, String type, Pageable pageable) {
        return repository.findAllByParams(userId.value(), type, pageable).map(this::toModel);
    }

    @Override
    @Transactional
    public Activity save(Activity model) {
        return toModel(repository.save(toEntity(model)));
    }

    private ActivityEntity toEntity(Activity model) {
        return ActivityEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .userId(model.getUserId().value())
                .type(model.getType())
                .message(model.getMessage().value())
                .createdAt(model.getCreatedAt().value())
                .build();
    }

    private Activity toModel(ActivityEntity entity) {
        return Activity.builder()
                .id(new ActivityId(entity.getId()))
                .userId(new UserId(entity.getUserId()))
                .type(entity.getType())
                .message(new ActivityMessage(entity.getMessage()))
                .createdAt(new ActivityCreationDate(entity.getCreatedAt()))
                .build();
    }
}
