package com.example.libraryapp.NEWinfrastructure.persistence.jpa.user;

import com.example.libraryapp.NEWdomain.user.model.User;
import com.example.libraryapp.NEWdomain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserRepositoryImpl implements UserRepository {
    private final JpaUserRepository repository;

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    @Transactional
    public User save(User user) {
        return toModel(repository.save(toEntity(user)));
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toModel);
    }

    @Override
    @Transactional
    public void incrementTotalBooksRequested(Long userId) {
        repository.incrementTotalBooksRequested(userId);
    }

    @Override
    public void decrementTotalBooksRequested(Long userId) {
        repository.decrementTotalBooksRequested(userId);
    }

    @Override
    public void incrementTotalBooksBorrowed(Long userId) {
        repository.incrementTotalBooksBorrowed(userId);
    }

    @Override
    public void decrementTotalBooksBorrowed(Long userId) {
        repository.decrementTotalBooksBorrowed(userId);
    }

    private UserEntity toEntity(User dto) {
        return new UserEntity(
                dto.getId(),
                dto.getRegistrationDate(),
                dto.getPassword(),
                dto.getEmail(),
                dto.getStatus(),
                dto.getRole(),
                dto.getTotalBooksBorrowed(),
                dto.getTotalBooksRequested(),
                dto.getCharge(),
                dto.getCardId(),
                dto.getPersonId()
        );
    }

    private User toModel(UserEntity entity) {
        return new User(
                entity.getId(),
                entity.getRegistrationDate(),
                entity.getPassword(),
                entity.getEmail(),
                entity.getStatus(),
                entity.getRole(),
                entity.getTotalBooksBorrowed(),
                entity.getTotalBooksRequested(),
                entity.getCharge(),
                entity.getCardId(),
                entity.getPersonId()
        );
    }
}
