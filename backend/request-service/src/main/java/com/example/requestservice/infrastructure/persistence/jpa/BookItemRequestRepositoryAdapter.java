package com.example.requestservice.infrastructure.persistence.jpa;

import com.example.requestservice.domain.model.BookItemRequest;
import com.example.requestservice.domain.model.values.*;
import com.example.requestservice.domain.ports.out.BookItemRequestRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookItemRequestRepositoryAdapter implements BookItemRequestRepositoryPort {
    private final JpaBookItemRequestRepository repository;

    @Override
    public Optional<BookItemRequest> findById(RequestId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public List<BookItemRequest> findAll(BookItemId bookItemId, UserId userId) {
        return repository.findAll(bookItemId.value(), userId.value())
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public List<BookItemRequest> findAllByUserIdAndStatuses(UserId userId, List<BookItemRequestStatus> statusesToFind) {
        return repository.findAllByUserIdAndStatuses(userId.value(), statusesToFind)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public List<BookItemRequest> findByBookItemIdAndStatuses(BookItemId bookItemId, List<BookItemRequestStatus> statusesToFind) {
        return repository.findAllByBookItemIdAndStatuses(bookItemId.value(), statusesToFind).stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public Page<BookItemRequest> findAllByStatus(BookItemRequestStatus status, Pageable pageable) {
        return repository.findAllByStatus(status, pageable).map(this::toModel);
    }

    @Override
    @Transactional
    public BookItemRequest save(BookItemRequest bookItemRequest) {
        BookItemRequestEntity entity = toEntity(bookItemRequest);
        return toModel(repository.save(entity));
    }

    @Override
    @Transactional
    public void setBookRequestStatus(RequestId id, BookItemRequestStatus status) {
        repository.setBookRequestStatus(id.value(), status);
    }

    BookItemRequestEntity toEntity(BookItemRequest model) {
        return new BookItemRequestEntity(
                model.getId() != null ? model.getId().value() : null,
                model.getCreationDate().value(),
                model.getStatus(),
                model.getUserId().value(),
                model.getBookItemId().value()
        );
    }

    BookItemRequest toModel(BookItemRequestEntity entity) {
        return new BookItemRequest(
                new RequestId(entity.getId()),
                new BookItemRequestCreationDate(entity.getCreationDate()),
                entity.getStatus(),
                new UserId(entity.getUserId()),
                new BookItemId(entity.getBookItemId())
        );
    }
}
