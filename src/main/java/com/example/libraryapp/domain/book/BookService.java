package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.book.mapper.BookToSaveDtoMapper;
import com.example.libraryapp.domain.exception.BookIsNotAvailableException;
import com.example.libraryapp.domain.exception.BookNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookDto> findAllBooks() {
        return StreamSupport.stream(bookRepository.findAll().spliterator(), false)
                .map(BookDtoMapper::map)
                .toList();
    }

    public Optional<BookDto> findBookById(Long id) {
        return bookRepository.findById(id)
                .map(BookDtoMapper::map);
    }

    public BookDto saveBook(BookToSaveDto book) {
        Book bookToSave = BookToSaveDtoMapper.map(book);
        bookToSave.setAvailability(Boolean.TRUE);
        Book savedBook = bookRepository.save(bookToSave);
        return BookDtoMapper.map(savedBook);
    }

    public Optional<BookDto> replaceBook(BookDto book) {
        Optional<Book> bookToUpdate = bookRepository.findById(book.getId());

        if (bookToUpdate.isPresent()) {
            Book bookToSave = BookDtoMapper.map(book);
            bookToSave.setCheckouts(bookToUpdate.get().getCheckouts());
            Book savedBook = bookRepository.save(bookToSave);
            return Optional.of(BookDtoMapper.map(savedBook));
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
        return BookDtoMapper.map(bookToUpdate);
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
