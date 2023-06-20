package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.book.mapper.BookToSaveDtoMapper;
import com.example.libraryapp.domain.config.assembler.BookModelAssembler;
import com.example.libraryapp.domain.exception.BookIsNotAvailableException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
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

//    public List<BookDto> findAllBooks() {
//        return bookRepository.findAll().stream()
//                .map(BookDtoMapper::map)
//                .toList();
//    }

    public PagedModel<BookDto> findAllBooks(Pageable pageable) {
        Page<Book> bookDtoPage =
                pageable.isUnpaged() ? new PageImpl<>(bookRepository.findAll()) : bookRepository.findAll(pageable);
        return pagedResourcesAssembler.toModel(bookDtoPage, bookModelAssembler);
    }

    public Optional<BookDto> findBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookModelAssembler::toModel);
    }

    public BookDto saveBook(BookToSaveDto book) {
        Book bookToSave = BookToSaveDtoMapper.map(book);
        bookToSave.setAvailability(Boolean.TRUE);
        Book savedBook = bookRepository.save(bookToSave);
        return bookModelAssembler.toModel(savedBook);
    }

    public Optional<BookDto> replaceBook(BookDto book) {
        Optional<Book> bookToUpdate = bookRepository.findById(book.getId());

        if (bookToUpdate.isPresent()) {
            Book bookToSave = BookDtoMapper.map(book);
            bookToSave.setCheckouts(bookToUpdate.get().getCheckouts());
            Book savedBook = bookRepository.save(bookToSave);
            return Optional.of(bookModelAssembler.toModel(savedBook));
        }
        return Optional.empty();
    }

    @Transactional
    public BookDto updateBook(Long id, BookDto book) {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        if (book != null) {
            if (book.getTitle() != null) bookToUpdate.setTitle(book.getTitle());
            if (book.getAuthor() != null) bookToUpdate.setAuthor(book.getAuthor());
            if (book.getPublisher() != null) bookToUpdate.setPublisher(book.getPublisher());
            if (book.getRelease_year() != null) bookToUpdate.setRelease_year(book.getRelease_year());
            if (book.getPages() != null) bookToUpdate.setPages(book.getPages());
            if (book.getIsbn() != null) bookToUpdate.setIsbn(book.getIsbn());
        } else {
            throw new NullPointerException();
        }
        return bookModelAssembler.toModel(bookToUpdate);
    }

    public void deleteBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        boolean bookIsNotAvailable = !book.getAvailability();
        if (bookIsNotAvailable) {
            throw new BookIsNotAvailableException();
        }
        bookRepository.deleteById(id);
    }
}
