package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.book.mapper.BookToSaveDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
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
        Book savedBook = bookRepository.save(bookToSave);
        return BookDtoMapper.map(savedBook);
    }

    public Optional<BookDto> replaceBook(Long id, BookDto book) {
        if (!bookRepository.existsById(id)) {
            return Optional.empty();
        }
        book.setId(id);
        Book bookToUpdate = BookDtoMapper.map(book);
        Book savedBook = bookRepository.save(bookToUpdate);
        return Optional.of(BookDtoMapper.map(savedBook));
    }

    @Transactional
    public void updateBook(Long id, BookDto book) {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

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
    }

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
