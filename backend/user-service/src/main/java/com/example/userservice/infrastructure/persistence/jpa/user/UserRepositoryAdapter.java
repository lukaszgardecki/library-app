package com.example.userservice.infrastructure.persistence.jpa.user;

import com.example.userservice.domain.model.librarycard.LibraryCardId;
import com.example.userservice.domain.model.person.PersonId;
import com.example.userservice.domain.model.fine.FineAmount;
import com.example.userservice.domain.model.user.*;
import com.example.userservice.domain.ports.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class UserRepositoryAdapter implements UserRepositoryPort {
    private final JpaUserRepository repository;

    @Override
    public List<User> findAllByLoansCountDesc(int limit) {
        return repository.findAllByLoansCountDesc(limit)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(this::toModel);
    }

    @Override
    public Page<UserListPreviewProjection> findAllListPreviews(String query, Pageable pageable) {
        return repository.findAllByQuery(query, pageable);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Optional<User> findByPersonId(PersonId personId) {
        return repository.findByPersonId(personId.value()).map(this::toModel);
    }

    @Override
    @Transactional
    public User save(User user) {
        return toModel(repository.save(toEntity(user)));
    }

    @Override
    @Transactional
    public void deleteById(UserId id) {
        repository.deleteById(id.value());
    }

    @Override
    @Transactional
    public void incrementTotalBooksRequested(UserId userId) {
        repository.incrementTotalBooksRequested(userId.value());
    }

    @Override
    @Transactional
    public void decrementTotalBooksRequested(UserId userId) {
        repository.decrementTotalBooksRequested(userId.value());
    }

    @Override
    @Transactional
    public void incrementTotalBooksBorrowed(UserId userId) {
        repository.incrementTotalBooksBorrowed(userId.value());
    }

    @Override
    @Transactional
    public void decrementTotalBooksBorrowed(UserId userId) {
        repository.decrementTotalBooksBorrowed(userId.value());
    }

    @Override
    @Transactional
    public void reduceChargeByAmount(UserId userId, FineAmount amount) {
        repository.reduceChargeByAmount(userId.value(), amount.value());
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
                model.getId() != null ? model.getId().value() : null,
                model.getRegistrationDate().value(),
                model.getTotalBooksBorrowed().value(),
                model.getTotalBooksRequested().value(),
                model.getCharge().value(),
                model.getCardId() != null ? model.getCardId().value() : null,
                model.getPersonId().value()
        );
    }

    private User toModel(UserEntity entity) {
        return new User(
                new UserId(entity.getId()),
                new RegistrationDate(entity.getRegistrationDate()),
                new TotalBooksBorrowed(entity.getTotalBooksBorrowed()),
                new TotalBooksRequested(entity.getTotalBooksRequested()),
                new UserCharge(entity.getCharge()),
                new LibraryCardId(entity.getCardId()),
                new PersonId(entity.getPersonId())
        );
    }
}
