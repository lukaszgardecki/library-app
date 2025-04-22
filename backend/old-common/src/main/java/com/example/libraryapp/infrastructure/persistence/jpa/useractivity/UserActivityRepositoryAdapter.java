package com.example.libraryapp.infrastructure.persistence.jpa.useractivity;

import com.example.libraryapp.domain.user.model.UserId;
import com.example.libraryapp.domain.useractivity.model.UserActivity;
import com.example.libraryapp.domain.useractivity.model.UserActivityCreationDate;
import com.example.libraryapp.domain.useractivity.model.UserActivityId;
import com.example.libraryapp.domain.useractivity.model.UserActivityMessage;
import com.example.libraryapp.domain.useractivity.ports.UserActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserActivityRepositoryAdapter implements UserActivityRepositoryPort {
    private final JpaUserActivityRepository repository;

    @Override
    public Optional<UserActivity> findById(UserActivityId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Page<UserActivity> findAllByParams(UserId userId, String type, Pageable pageable) {
        return repository.findAllByParams(userId.value(), type, pageable).map(this::toModel);
    }

    @Override
    @Transactional
    public UserActivity save(UserActivity model) {
        return toModel(repository.save(toEntity(model)));
    }

    private UserActivityEntity toEntity(UserActivity model) {
        return UserActivityEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .userId(model.getUserId().value())
                .type(model.getType())
                .message(model.getMessage().value())
                .createdAt(model.getCreatedAt().value())
                .build();
    }

    private UserActivity toModel(UserActivityEntity entity) {
        return UserActivity.builder()
                .id(new UserActivityId(entity.getId()))
                .userId(new UserId(entity.getUserId()))
                .type(entity.getType())
                .message(new UserActivityMessage(entity.getMessage()))
                .createdAt(new UserActivityCreationDate(entity.getCreatedAt()))
                .build();
    }
}
