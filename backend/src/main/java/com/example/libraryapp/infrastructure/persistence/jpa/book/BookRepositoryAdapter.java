package com.example.libraryapp.infrastructure.persistence.jpa.book;

import com.example.libraryapp.domain.book.model.Book;
import com.example.libraryapp.domain.book.ports.BookRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookRepositoryAdapter implements BookRepositoryPort {
    private final JpaBookRepository repository;

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id).map(this::toModel);
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return toModel(repository.save(toEntity(book)));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    private BookEntity toEntity(Book model) {
        return new BookEntity(
                model.getId(),
                model.getTitle(),
                model.getSubject(),
                model.getPublisher(),
                model.getISBN(),
                model.getLanguage(),
                model.getPages()
        );
    }

    private Book toModel(BookEntity entity) {
        return new Book(
                entity.getId(),
                entity.getTitle(),
                entity.getSubject(),
                entity.getPublisher(),
                entity.getISBN(),
                entity.getLanguage(),
                entity.getPages()
        );
    }
}
