package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.book.mapper.BookMapper;
import com.example.libraryapp.domain.config.assembler.BookModelAssembler;
import com.example.libraryapp.domain.exception.book.BookNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public BookDto findBookById(Long id) {
        Book book = findBook(id);
        return bookModelAssembler.toModel(book);
    }

    public BookDto saveBook(BookToSaveDto book) {
        Book bookToSave = BookMapper.map(book);
        Book savedBook = bookRepository.save(bookToSave);
        return bookModelAssembler.toModel(savedBook);
    }

    public BookDto replaceBook(Long bookId, BookDto book) {
        Book bookToReplace = findBook(bookId);
        bookToReplace.setTitle(book.getTitle());
        bookToReplace.setSubject(book.getSubject());
        bookToReplace.setPublisher(book.getPublisher());
        bookToReplace.setISBN(book.getISBN());
        bookToReplace.setLanguage(book.getLanguage());
        bookToReplace.setPages(book.getPages());
        Book savedBook = bookRepository.save(bookToReplace);
        return bookModelAssembler.toModel(savedBook);

    }

    @Transactional
    public BookDto updateBook(Long bookId, BookDto book) {
        Book bookToUpdate = findBook(bookId);
        if (book.getTitle() != null) bookToUpdate.setTitle(book.getTitle());
        if (book.getSubject() != null) bookToUpdate.setSubject(book.getSubject());
        if (book.getPublisher() != null) bookToUpdate.setPublisher(book.getPublisher());
        if (book.getISBN() != null) bookToUpdate.setISBN(book.getISBN());
        if (book.getLanguage() != null) bookToUpdate.setLanguage(book.getLanguage());
        if (book.getPages() != null) bookToUpdate.setPages(book.getPages());
        if (book.getBookItems() != null) bookToUpdate.setBookItems(book.getBookItems());
        return bookModelAssembler.toModel(bookToUpdate);
    }

    public void deleteBook(Long bookId) {
        Book bookToDelete = findBook(bookId);
        bookRepository.delete(bookToDelete);
    }

    private Book findBook(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
    }
}
