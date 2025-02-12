package com.example.libraryapp.infrastructure.persistence.jpa.user;

import com.example.libraryapp.domain.user.model.User;
import com.example.libraryapp.domain.user.ports.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserRepositoryAdapter implements UserRepository {
    private final JpaUserRepository repository;

    @Override
    public List<User> findAllByLoansCountDesc(int limit) {
        return repository.findAllByLoansCountDesc(limit)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Optional<User> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmail(email).map(this::toModel);
    }

    @Override
    public Optional<User> findByPersonId(Long personId) {
        return repository.findByPersonId(personId).map(this::toModel);
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
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional
    public void incrementTotalBooksRequested(Long userId) {
        repository.incrementTotalBooksRequested(userId);
    }

    @Override
    @Transactional
    public void decrementTotalBooksRequested(Long userId) {
        repository.decrementTotalBooksRequested(userId);
    }

    @Override
    @Transactional
    public void incrementTotalBooksBorrowed(Long userId) {
        repository.incrementTotalBooksBorrowed(userId);
    }

    @Override
    @Transactional
    public void decrementTotalBooksBorrowed(Long userId) {
        repository.decrementTotalBooksBorrowed(userId);
    }

    @Override
    @Transactional
    public void reduceChargeByAmount(Long userId, BigDecimal amount) {
        repository.reduceChargeByAmount(userId, amount);
    }

    @Override
    public long count() {
        return repository.count();
    }

    @Override
    public long countNewRegisteredUsersByMonth(int month, int year) {
        return repository.countNewRegisteredUsersByMonth(month, year);
    }

    private UserEntity toEntity(User model) {
        return new UserEntity(
                model.getId(),
                model.getRegistrationDate(),
                model.getPassword(),
                model.getEmail(),
                model.getStatus(),
                model.getRole(),
                model.getTotalBooksBorrowed(),
                model.getTotalBooksRequested(),
                model.getCharge(),
                model.getCardId(),
                model.getPersonId()
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
