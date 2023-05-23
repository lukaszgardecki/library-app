package com.example.libraryapp.web;

import com.example.libraryapp.domain.book.dto.BookDto;
import com.example.libraryapp.domain.book.BookService;
import com.example.libraryapp.domain.book.dto.BookToSaveDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books")
    public List<BookDto> getAllBooks() {
        return bookService.findAllBooks();
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> addBook(@RequestBody BookToSaveDto book) {
        BookDto savedBook = bookService.saveBook(book);
        URI savedBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedBook.getId())
                .toUri();
        return ResponseEntity.created(savedBookUri).body(savedBook);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        return bookService.findBookById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/books/{id}")
    ResponseEntity<?> replaceBook(@PathVariable Long id, @RequestBody BookDto book) {
        return bookService.replaceBook(id, book)
                .map(b -> ResponseEntity.noContent().build())
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> mismatchExceptionHandler() {
        return ResponseEntity.badRequest().body("Id must be a number.");
    }
}
