package com.example.libraryapp.infrastructure.persistence.jpa.bookitemrequest;

import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequest;
import com.example.libraryapp.domain.bookitemrequest.model.BookItemRequestStatus;
import com.example.libraryapp.domain.bookitemrequest.ports.BookItemRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookItemRequestRepositoryAdapter implements BookItemRequestRepository {
    private final JpaBookItemRequestRepository repository;

    @Override
    public Optional<BookItemRequest> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public List<BookItemRequest> findAll(Long bookItemId, Long userId) {
        return repository.findAll(bookItemId, userId)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public List<BookItemRequest> findAllByUserIdAndStatuses(Long userId, List<BookItemRequestStatus> statusesToFind) {
        return repository.findAllByUserIdAndStatuses(userId, statusesToFind)
                .stream()
                .map(this::toModel)
                .toList();
    }

    @Override
    public List<BookItemRequest> findByBookItemIdAndStatuses(Long bookItemId, List<BookItemRequestStatus> statusesToFind) {
        return repository.findAllByBookItemIdAndStatuses(bookItemId, statusesToFind).stream()
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
    public void setBookRequestStatus(Long id, BookItemRequestStatus status) {
        repository.setBookRequestStatus(id, status);
    }

    BookItemRequestEntity toEntity(BookItemRequest model) {
        return new BookItemRequestEntity(
                model.getId(),
                model.getCreationDate(),
                model.getStatus(),
                model.getUserId(),
                model.getBookItemId()
        );
    }

    BookItemRequest toModel(BookItemRequestEntity entity) {
        return new BookItemRequest(
                entity.getId(),
                entity.getCreationDate(),
                entity.getStatus(),
                entity.getUserId(),
                entity.getBookItemId()
        );
    }
}
