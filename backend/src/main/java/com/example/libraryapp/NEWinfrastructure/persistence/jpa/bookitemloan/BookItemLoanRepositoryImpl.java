package com.example.libraryapp.NEWinfrastructure.persistence.jpa.bookitemloan;

import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoan;
import com.example.libraryapp.NEWdomain.bookitemloan.model.BookItemLoanStatus;
import com.example.libraryapp.NEWdomain.bookitemloan.ports.BookItemLoanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookItemLoanRepositoryImpl implements BookItemLoanRepository {
    private final JpaBookItemLoanRepository repository;

    @Override
    public Optional<BookItemLoan> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    public Optional<BookItemLoan> findByParams(Long userId, Long bookItemId, BookItemLoanStatus status) {
        return repository.findByParams(userId, bookItemId, status).map(this::toModel);
    }

    @Override
    public Page<BookItemLoan> findPageOfBookLoansByParams(Long id, BookItemLoanStatus status, Pageable pageable) {
        return repository.findAllByParams(id, status, pageable).map(this::toModel);
    }

    @Override
    public BookItemLoan save(BookItemLoan bookItemLoan) {
        return toModel(repository.save(toEntity(bookItemLoan)));
    }

    private BookItemLoanEntity toEntity(BookItemLoan model) {
        return BookItemLoanEntity.builder()
                .id(model.getId())
                .creationDate(model.getCreationDate())
                .dueDate(model.getDueDate())
                .returnDate(model.getReturnDate())
                .status(model.getStatus())
                .userId(model.getUserId())
                .bookItemId(model.getBookItemId())
                .build();
    }

    private BookItemLoan toModel(BookItemLoanEntity entity) {
        return BookItemLoan.builder()
                .id(entity.getId())
                .creationDate(entity.getCreationDate())
                .dueDate(entity.getDueDate())
                .returnDate(entity.getReturnDate())
                .status(entity.getStatus())
                .userId(entity.getUserId())
                .bookItemId(entity.getBookItemId())
                .build();
    }
}
