package com.example.libraryapp.infrastructure.persistence.jpa.bookitem;

import com.example.libraryapp.domain.book.model.BookId;
import com.example.libraryapp.domain.bookitem.model.*;
import com.example.libraryapp.domain.bookitem.ports.BookItemRepositoryPort;
import com.example.libraryapp.domain.bookitemloan.model.LoanCreationDate;
import com.example.libraryapp.domain.bookitemloan.model.LoanDueDate;
import com.example.libraryapp.domain.rack.model.RackId;
import com.example.libraryapp.domain.shelf.model.ShelfId;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookItemRepositoryAdapter implements BookItemRepositoryPort {
    private final JpaBookItemRepository repository;

    @Override
    public Optional<BookItem> findById(BookItemId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    public Page<BookItem> findAllByParams(BookId bookId, RackId rackId, ShelfId shelfId, Pageable pageable) {
        return repository.findAllByParams(bookId.value(), rackId.value(), shelfId.value(), pageable).map(this::toModel);
    }

    @Override
    @Transactional
    public BookItem save(BookItem bookItem) {
        BookItemEntity savedBookItem = repository.save(toEntity(bookItem));
        return toModel(savedBookItem);
    }

    @Override
    @Transactional
    public void updateStatus(BookItemId id, BookItemStatus status) {
        repository.updateStatus(id.value(), status);
    }

    @Override
    public void updateBarcode(BookItemId id, BookItemBarcode barcode) {
        repository.updateBarcode(id.value(), barcode.value());
    }

    @Override
    @Transactional
    public void deleteById(BookItemId id) {
        repository.deleteById(id.value());
    }

    private BookItemEntity toEntity(BookItem model) {
        return BookItemEntity.builder()
                .id(model.getId() != null ? model.getId().value() : null)
                .barcode(model.getBarcode().value())
                .isReferenceOnly(model.getIsReferenceOnly().value())
                .borrowed(model.getBorrowedDate() != null ? model.getBorrowedDate().value().toLocalDate() : null)
                .dueDate(model.getDueDate() != null ? model.getDueDate().value().toLocalDate() : null)
                .price(model.getPrice().value())
                .status(model.getStatus())
                .dateOfPurchase(model.getDateOfPurchase().value())
                .bookId(model.getBookId().value())
                .rackId(model.getRackId().value())
                .shelfId(model.getShelfId().value())
                .build();
    }

    private BookItem toModel(BookItemEntity entity) {
        return BookItem.builder()
                .id(new BookItemId(entity.getId()))
                .barcode(new BookItemBarcode(entity.getBarcode()))
                .isReferenceOnly(new IsReferenceOnly(entity.getIsReferenceOnly()))
                .borrowedDate(new LoanCreationDate(entity.getBorrowed() != null ? entity.getBorrowed().atStartOfDay() : null ))
                .dueDate(new LoanDueDate(entity.getDueDate() != null ? entity.getDueDate().atStartOfDay() : null ))
                .price(new Price(entity.getPrice()))
                .status(entity.getStatus())
                .dateOfPurchase(new PurchaseDate(entity.getDateOfPurchase()))
                .bookId(new BookId(entity.getBookId()))
                .rackId(new RackId(entity.getRackId()))
                .shelfId(new ShelfId(entity.getShelfId()))
                .build();
    }


}
