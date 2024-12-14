package com.example.libraryapp.NEWinfrastructure.persistence.jpa.useractivity;

import com.example.libraryapp.NEWdomain.useractivity.model.UserActivity;
import com.example.libraryapp.NEWdomain.useractivity.ports.UserActivityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserActivityRepositoryImpl implements UserActivityRepository {
    private final JpaUserActivityRepository repository;


    @Override
    public Optional<UserActivity> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public Page<UserActivity> findAllByParams(Long userId, String type, Pageable pageable) {
        return repository.findAllByParams(userId, type, pageable).map(this::toModel);
    }

    @Override
    public UserActivity save(UserActivity model) {
        return toModel(repository.save(toEntity(model)));
    }

    private UserActivityEntity toEntity(UserActivity model) {
        return UserActivityEntity.builder()
                .id(model.getId())
                .userId(model.getUserId())
                .type(model.getType())
                .message(model.getMessage())
                .createdAt(model.getCreatedAt())
                .build();
    }

    private UserActivity toModel(UserActivityEntity entity) {
        return UserActivity.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .type(entity.getType())
                .message(entity.getMessage())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
