package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.mapper.BookMapper;
import com.example.libraryapp.domain.config.assembler.BookModelAssembler;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookModelAssembler bookModelAssembler;
    private final PagedResourcesAssembler<Book> pagedResourcesAssembler;

    public BookService(BookRepository bookRepository,
                       BookModelAssembler bookModelAssembler,
                       PagedResourcesAssembler<Book> pagedResourcesAssembler) {
        this.bookRepository = bookRepository;
        this.bookModelAssembler = bookModelAssembler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }


    public PagedModel<BookDto> findAllBook(Pageable pageable) {
        Page<Book> bookPage =
                pageable.isUnpaged() ? new PageImpl<>(bookRepository.findAll()) : bookRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(bookPage, bookModelAssembler);
    }

    public Optional<BookDto> findBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookModelAssembler::toModel);
    }

    public BookDto saveBook(BookDto book) {
        Book bookToSave = BookMapper.map(book);
        Book savedBook = bookRepository.save(bookToSave);
        return bookModelAssembler.toModel(savedBook);
    }

    public Optional<BookDto> replaceBook(Long bookId, BookDto book) {
        Optional<Book> optToReplace = bookRepository.findById(bookId);
        if (optToReplace.isPresent()) {
            Book bookToSave = BookMapper.map(book);
            bookToSave.setId(bookId);
            Book savedBook = bookRepository.save(bookToSave);
            return Optional.of(bookModelAssembler.toModel(savedBook));
        }
        return Optional.empty();
    }

    @Transactional
    public Optional<BookDto> updateBook(Long bookId, BookDto book) {
        Optional<Book> optToUpdate = bookRepository.findById(bookId);
        if (optToUpdate.isPresent()) {
            Book bookToUpdate = optToUpdate.get();
            if (book.getTitle() != null) bookToUpdate.setTitle(book.getTitle());
            if (book.getSubject() != null) bookToUpdate.setSubject(book.getSubject());
            if (book.getPublisher() != null) bookToUpdate.setPublisher(book.getPublisher());
            if (book.getISBN() != null) bookToUpdate.setISBN(book.getISBN());
            if (book.getLanguage() != null) bookToUpdate.setLanguage(book.getLanguage());
            if (book.getPages() != null) bookToUpdate.setPages(book.getPages());
            if (book.getBookItems() != null) bookToUpdate.setBookItems(book.getBookItems());
            return Optional.of(bookModelAssembler.toModel(bookToUpdate));
        }
        return Optional.empty();
    }

    public boolean deleteBook(Long bookId) {
        Optional<Book> optToDelete = bookRepository.findById(bookId);
        if (optToDelete.isPresent()) {
            bookRepository.deleteById(bookId);
            return true;
        }
        return false;
    }
}
