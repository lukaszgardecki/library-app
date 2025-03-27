package com.example.libraryapp.infrastructure.persistence.jpa.book;

import com.example.libraryapp.domain.book.model.*;
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
    public Optional<Book> findById(BookId id) {
        return repository.findById(id.value()).map(this::toModel);
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return toModel(repository.save(toEntity(book)));
    }

    @Override
    @Transactional
    public void deleteById(BookId id) {
        repository.deleteById(id.value());
    }

    private BookEntity toEntity(Book model) {
        return new BookEntity(
                model.getId() != null ? model.getId().value() : null,
                model.getTitle().value(),
                model.getSubject().value(),
                model.getPublisher().value(),
                model.getISBN().value(),
                model.getLanguage().value(),
                model.getPages().value()
        );
    }

    private Book toModel(BookEntity entity) {
        return new Book(
                new BookId(entity.getId()),
                new Title(entity.getTitle()),
                new Subject(entity.getSubject()),
                new Publisher(entity.getPublisher()),
                new Isbn(entity.getISBN()),
                new Language(entity.getLanguage()),
                new Pages(entity.getPages())
        );
    }
}
