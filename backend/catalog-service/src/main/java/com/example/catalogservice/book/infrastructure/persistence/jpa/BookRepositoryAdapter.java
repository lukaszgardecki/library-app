package com.example.catalogservice.book.infrastructure.persistence.jpa;

import com.example.catalogservice.book.domain.model.*;
import com.example.catalogservice.book.domain.ports.BookRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class BookRepositoryAdapter implements BookRepositoryPort {
    private final JpaBookRepository repository;

    @Override
    public List<Book> findAllById(List<BookId> ids) {
        List<Long> idList = ids.stream().map(BookId::value).toList();
        return repository.findAllById(idList).stream()
                .map(this::toModel)
                .toList();
    }

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
                model.getPages().value(),
                model.getFormat(),
                model.getPublicationDate().value()
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
                new Pages(entity.getPages()),
                entity.getFormat(),
                new PublicationDate(entity.getPublicationDate())
        );
    }
}
