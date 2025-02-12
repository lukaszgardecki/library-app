package com.example.libraryapp.infrastructure.persistence.jpa.bookitem;

import com.example.libraryapp.domain.bookitem.model.BookItem;
import com.example.libraryapp.domain.bookitem.model.BookItemStatus;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookItemRepositoryAdapter implements BookItemRepository {
    private final JpaBookItemRepository repository;

    @Override
    public Optional<BookItem> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public Page<BookItem> getPageOfBookItems(Pageable pageable) {
        return repository.findAllByParams(null, pageable).map(this::toModel);
    }

    @Override
    public Page<BookItem> getPageOfBookItemsByBookId(Long bookId, Pageable pageable) {
        return repository.findAllByParams(bookId, pageable).map(this::toModel);
    }

    @Override
    @Transactional
    public BookItem save(BookItem bookItem) {
        BookItemEntity savedBookItem = repository.save(toEntity(bookItem));
        return toModel(savedBookItem);
    }

    @Override
    @Transactional
    public void updateStatus(Long id, BookItemStatus status) {
        repository.updateStatus(id, status);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private BookItemEntity toEntity(BookItem model) {
        return BookItemEntity.builder()
                .id(model.getId())
                .barcode(model.getBarcode())
                .isReferenceOnly(model.getIsReferenceOnly())
                .borrowed(model.getBorrowed())
                .dueDate(model.getDueDate())
                .price(model.getPrice())
                .format(model.getFormat())
                .status(model.getStatus())
                .dateOfPurchase(model.getDateOfPurchase())
                .publicationDate(model.getPublicationDate())
                .bookId(model.getBookId())
                .rackId(model.getRackId())
                .build();
    }

    private BookItem toModel(BookItemEntity entity) {
        return BookItem.builder()
                .id(entity.getId())
                .barcode(entity.getBarcode())
                .isReferenceOnly(entity.getIsReferenceOnly())
                .borrowed(entity.getBorrowed())
                .dueDate(entity.getDueDate())
                .price(entity.getPrice())
                .format(entity.getFormat())
                .status(entity.getStatus())
                .dateOfPurchase(entity.getDateOfPurchase())
                .publicationDate(entity.getPublicationDate())
                .bookId(entity.getBookId())
                .rackId(entity.getRackId())
                .build();
    }


}
