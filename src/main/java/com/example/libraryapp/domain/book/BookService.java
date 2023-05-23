package com.example.libraryapp.domain.book;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import com.example.libraryapp.domain.book.mapper.BookDtoMapper;
import com.example.libraryapp.domain.book.mapper.BookToSaveDtoMapper;
import org.springframework.stereotype.Service;

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

    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }
}
